package me.hellozin.todolist.todo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@ToString
public class Todo {

    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    private String author;

    @NotBlank
    private String title;

    private String content;

    @Min(1)
    @Max(5)
    private int importance;

    @NotNull
    private boolean done;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    public boolean isExpired() {
        return (deadline == null ? LocalDate.MAX : deadline).isBefore(LocalDate.now());
    }

    public void updateTodo(Todo changedTodo) {
        this.title = changedTodo.getTitle();
        this.content = changedTodo.getContent();
        this.importance = changedTodo.getImportance();
        this.deadline = changedTodo.getDeadline();
    }

}
