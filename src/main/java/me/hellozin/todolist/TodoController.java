package me.hellozin.todolist;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/list")
    @ResponseBody
    public String getTodoList(@CookieValue String user) {
        List<Todo> todoList = todoRepository.findAllByAuthor(user);
        return todoList.get(0).getContent();
    }

}
