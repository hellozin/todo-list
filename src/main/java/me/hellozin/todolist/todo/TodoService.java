package me.hellozin.todolist.todo;

import me.hellozin.todolist.exceptions.TodoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Optional<String> getAuthorById(long id) {
        return todoRepository.findById(id).map(Todo::getAuthor);
    }

    public void createTodo(Todo todo, String author) {
        todo.setAuthor(author);
        todo.setDone(false);
        todoRepository.save(todo);
    }

    public List<Todo> getTodoList(String author) {
        return todoRepository.findAllByAuthorOrderByDoneAscImportanceDesc(author);
    }

    public void updateTodo(Todo updatedTodo) {
        Todo todoById = todoRepository.findById(updatedTodo.getId())
                .orElseThrow(TodoException::new);
        todoById.setTitle(updatedTodo.getTitle());
        todoById.setContent(updatedTodo.getContent());
        todoById.setImportance(updatedTodo.getImportance());
        todoById.setDeadline(updatedTodo.getDeadline());
    }

    public void deleteTodo(long idForDelete) {
        todoRepository.delete(todoRepository.findById(idForDelete).orElseThrow(TodoException::new));
    }

    public void toggleDone(long idForToggleDone) {
        Todo todoById = todoRepository.findById(idForToggleDone).orElseThrow(TodoException::new);
        todoById.setDone(!todoById.isDone());
        todoRepository.save(todoById);
    }

}
