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
    public String doLogin(@RequestParam String currentUser, HttpServletResponse response) {
        response.addCookie(new Cookie("currentUser", currentUser));
        return "redirect:/todos";
    }

    @GetMapping("/todos")
    public String getTodoList(@ModelAttribute Todo todo,
                              Map<String, Object> model,
                              @CookieValue String currentUser) {
        model.put("todoList", todoService.getTodoList(currentUser));
        model.put("currentUser", currentUser);
        return "list";
    }

    @PostMapping("/todos")
    public String createTodo(@Valid @ModelAttribute Todo todo,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @CookieValue String currentUser) {
        if (!currentUser.equals(todo.getAuthor())) {
            throw new UnknownAuthorException();
        }
        if (bindingResult.hasErrors()) {
            String errMsg = todoService.getSimpleErrorMsg(bindingResult.getFieldErrors());
            redirectAttributes.addFlashAttribute("errMsg", errMsg);
            return "redirect:/todos";
        }
        todoService.createTodo(todo, currentUser);
        return "redirect:/todos";
    }

    @PutMapping("/todos/{id}")
    public String updateTodo(@PathVariable long id,
                             @Valid @ModelAttribute Todo changedTodo,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @CookieValue String currentUser) {
        String changedTodoAuthor = todoService.getAuthorById(id).orElse("!" + currentUser);
        if (!currentUser.equals(changedTodoAuthor)) {
            throw new UnknownAuthorException();
        }
        if (bindingResult.hasErrors()) {
            String errMsg = todoService.getSimpleErrorMsg(bindingResult.getFieldErrors());
            redirectAttributes.addFlashAttribute("errMsg", errMsg);
            return "redirect:/todos";
        }
        todoService.updateTodo(changedTodo);
        return "redirect:/todos";
    }

    @DeleteMapping("/todos/{id}")
    public String deleteTodo(@PathVariable long id,
                             @CookieValue String currentUser) {
        String deletedTodoAuthor = todoService.getAuthorById(id).orElse("!" + currentUser);
        if (!currentUser.equals(deletedTodoAuthor)) {
            throw new UnknownAuthorException();
        }
        todoService.deleteTodo(id);
        return "redirect:/todos";
    }

    @PutMapping("/todos/{id}/done")
    public String doneTodo(@PathVariable long id,
                           @CookieValue String currentUser) {
        String toggleDoneAuthor = todoService.getAuthorById(id).orElse("!" + currentUser);
        if (!currentUser.equals(toggleDoneAuthor)) {
            throw new UnknownAuthorException();
        }
        todoService.toggleDone(id);
        return "redirect:/todos";
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
