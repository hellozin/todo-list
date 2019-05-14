package me.hellozin.todolist;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TodoListApplicationTests {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void login() throws Exception {
        mockMvc.perform(post("/login")
                .param("name", "hellozin")
        )
                .andDo(print())
                .andExpect(cookie().value("author", "hellozin"));
    }

    @Test
    public void showTodoList() throws Exception {
        Todo todo = new Todo();
        todo.setAuthor("hellozin");
        todo.setTitle("New TODO");
        todo.setContent("testing");

        todoRepository.save(todo);

        MvcResult mvcResult = mockMvc.perform(get("/list")
                .cookie(new Cookie("author", "hellozin"))
        )
                .andExpect(status().isOk())
                .andReturn();

        List<Todo> todoList = (List) mvcResult.getModelAndView().getModel().get("todoList");
        assert todoList.get(0).getContent().equals("testing");
    }

    @Test
    public void createTodoTest() throws Exception {
        Todo todo = new Todo();
        todo.setAuthor("hellozin");
        todo.setTitle("New TODO");
        todo.setContent("testing");

        mockMvc.perform(post("/todo")
                .cookie(new Cookie("author", todo.getAuthor()))
                .param("title", todo.getTitle())
                .param("content", todo.getContent())
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        List<Todo> allByAuthor = todoRepository.findAllByAuthor(todo.getAuthor());
        assert allByAuthor.get(0).getContent().equals("testing");

    }

}
