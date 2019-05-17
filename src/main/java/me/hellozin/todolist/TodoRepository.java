package me.hellozin.todolist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByAuthor(String author);

    List<Todo> findAllByAuthorOrderByDoneAscImportanceDesc(String author);
}
