package me.hellozin.todolist;

import me.hellozin.todolist.exception.AuthorNotFoundException;
import me.hellozin.todolist.exception.TodoException;
import me.hellozin.todolist.exception.ValidatorException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
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
        List<Todo> todoList = todoRepository.findAllByAuthorOrderByDoneAscImportanceDesc(author);
        model.put("todoList", todoList);
        model.put("author", author);
        return "list";
    }

    @PostMapping("/todo")
    public String createTodo(@Valid @ModelAttribute Todo todo, BindingResult bindingResult, @CookieValue String author) {
        if (bindingResult.hasErrors()) {
            throw new ValidatorException(bindingResult);
        }
        todo.setAuthor(author);
        todo.setDone(false);
        todoRepository.save(todo);
        return "redirect:/list";
    }

    @PutMapping("/todo")
    public String updateTodo(@ModelAttribute Todo changedTodo, @CookieValue String author) {
        Todo baseTodo = todoRepository.findById(changedTodo.getId()).orElseThrow(TodoException::new);
        if (author.equals(baseTodo.getAuthor())) {
            baseTodo.updateTodo(changedTodo);
            todoRepository.save(baseTodo);
        } else {
            /* Author Not Match Error */
        }
        return "redirect:/list";
    }

    @DeleteMapping("/todo")
    public String deleteTodo(@RequestParam String id, @CookieValue String author) {
        Optional<Todo> todo = todoRepository.findById(Long.valueOf(id));
        String authorOfId = todo.map(Todo::getAuthor).orElse("");
        if (authorOfId.equals(author)) {
            todoRepository.deleteById(Long.valueOf(id));
        } else {
            /* Author Not Match Error */
        }

        return "redirect:/list";
    }

    @PutMapping("/todo/done")
    public String doneTodo(@RequestParam long id) {
        Optional<Todo> mayTodoById = todoRepository.findById(id);
        mayTodoById.ifPresent(todoById -> {
            todoById.setDone(!todoById.isDone());
            todoRepository.save(todoById);
        });

        return "redirect:/list";
    }

    @ExceptionHandler
    public String validate(ValidatorException exception, Model model) {
        return "list";
    }

    @ExceptionHandler({
            AuthorNotFoundException.class,
            TodoException.class
    })
    public String tokenNotValid(TodoException exception, Model model) {
        model.addAttribute("message", exception.getErrorMsg());
        return "error";
    }

}
