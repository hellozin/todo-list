package me.hellozin.todolist.todo;

import me.hellozin.todolist.exceptions.TodoException;
import me.hellozin.todolist.exceptions.UnknownAuthorException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@Controller
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String author, HttpServletResponse response) {
        response.addCookie(new Cookie("author", author));
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String getTodoList(@ModelAttribute Todo todo, Map<String, Object> model, @CookieValue String author) {
        model.put("todoList", todoService.getTodoList(author));
        model.put("author", author);
        return "list";
    }

    @PostMapping("/todo")
    public String createTodo(@Valid @ModelAttribute Todo todo, BindingResult bindingResult, RedirectAttributes redirectAttributes, @CookieValue String author) {
        if (!author.equals(todo.getAuthor())) {
            throw new UnknownAuthorException();
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errMsg", bindingResult.getFieldError());
            return "redirect:/list";
        }
        todoService.createTodo(todo, author);
        return "redirect:/list";
    }

    @PutMapping("/todo")
    public String updateTodo(@ModelAttribute Todo changedTodo, @CookieValue String author) {
        if (!author.equals(todoService.getAuthorById(changedTodo.getId()).orElse("Not" + author))) {
            throw new UnknownAuthorException();
        }
        todoService.updateTodo(changedTodo);
        return "redirect:/list";
    }

    @DeleteMapping("/todo")
    public String deleteTodo(@RequestParam long id, @CookieValue String author) {
        if (!author.equals(todoService.getAuthorById(id).orElse("Not" + author))) {
            throw new UnknownAuthorException();
        }
        todoService.deleteTodo(id);
        return "redirect:/list";
    }

    @PutMapping("/todo/done")
    public String doneTodo(@RequestParam long id, @CookieValue String author) {
        if (!author.equals(todoService.getAuthorById(id).orElse("Not" + author))) {
            throw new UnknownAuthorException();
        }
        todoService.toggleDone(id);
        return "redirect:/list";
    }

    @ExceptionHandler({
            UnknownAuthorException.class,
            TodoException.class
    })
    public String todoExceptionHandler(TodoException exception, Model model) {
        model.addAttribute("message", exception.getErrorMsg());
        return "error";
    }

}
