package me.hellozin.todolist;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/login")
    public String loginForm(User user) {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(User user, HttpServletResponse response) {
        response.addCookie(new Cookie("author", user.getName()));
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String getTodoList(@CookieValue String author, Model model) {
        List<Todo> todoList = todoRepository.findAllByAuthor(author);
        model.addAttribute("todoList", todoList);
        return "list";
    }

}
