package me.hellozin.todolist;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
    public String loginForm(User author) {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(User author, HttpServletResponse response) {
        response.addCookie(new Cookie("author", author.getName()));
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String getTodoList(Todo todo, Map<String, Object> model, @CookieValue String author) {
        List<Todo> todoList = todoRepository.findAllByAuthor(author);
        model.put("todoList", todoList);
        model.put("author", author);
        return "list";
    }

    @PostMapping("/todo")
    public String createTodo(Todo todo, @CookieValue String author) {
        todo.setAuthor(author);
        todoRepository.save(todo);
        return "redirect:/list";
    }

    @PutMapping("/todo")
    public String updateTodo(Todo changedTodo, @CookieValue String author) {
        Todo baseTodo = todoRepository.findById(changedTodo.getId()).orElse(changedTodo);
        if (author.equals(baseTodo.getAuthor())) {
            baseTodo.setTitle(changedTodo.getTitle());
            baseTodo.setContent(changedTodo.getContent());
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

}
