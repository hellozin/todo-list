package me.hellozin.todolist;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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

}
