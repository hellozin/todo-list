package me.hellozin.todolist;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TodoListApplicationTests {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    MockMvc mockMvc;

    private Todo todoForTest;

    @Before
    public void cleanRepository() {
        todoRepository.deleteAll();

        todoForTest = new Todo();
        todoForTest.setAuthor("hellozin");
        todoForTest.setTitle("New TODO");
        todoForTest.setContent("testing");

        todoRepository.save(todoForTest);
    }

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
        MvcResult mvcResult = mockMvc.perform(get("/list")
                .cookie(new Cookie("author", todoForTest.getAuthor()))
        )
                .andExpect(status().isOk())
                .andReturn();

        List<Todo> todoList = (List) mvcResult.getModelAndView().getModel().get("todoList");
        assert todoList.get(0).getContent().equals(todoForTest.getContent());
    }

    @Test
    public void createTodoTest() throws Exception {
        mockMvc.perform(post("/todo")
                .cookie(new Cookie("author", "hellozin"))
                .param("title", "Another TODO")
                .param("content", "create testing")
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        List<Todo> allByAuthor = todoRepository.findAllByAuthor("hellozin");
        assert allByAuthor.get(1).getContent().equals("create testing");

    }

    @Test
    public void deleteTodoTest() throws Exception {
        Todo todoForDelete = todoRepository.findAllByAuthor(todoForTest.getAuthor()).get(0);

        mockMvc.perform(delete("/todo")
                .param("id", String.valueOf(todoForDelete.getId()))
                .cookie(new Cookie("author", todoForTest.getAuthor()))
        )
                .andExpect(status().is3xxRedirection());

        assert todoRepository.count() == 0;
    }

    @Test
    public void updateTodoTest() throws Exception {
        mockMvc.perform(put("/todo")
                .param("id", String.valueOf(todoForTest.getId()))
                .param("title", "Changed Title")
                .param("content", "Changed Content")
        )
                .andExpect(status().is3xxRedirection());

        Optional<Todo> changedTodo = todoRepository.findById(todoForTest.getId());
        assert changedTodo.orElse(todoForTest).getTitle().equals("Changed Title");
    }
}
