package me.hellozin.todolist;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@ToString
public class Todo {

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private String author;

    @NonNull
    private String title;

    private String content;

    @Min(1)
    @Max(5)
    private int importance;

    @NonNull
    private boolean done;

    @DateTimeFormat(pattern = "yy-MM-dd")
    private LocalDate deadline;

    public boolean isExpired() {
        return (deadline == null ? LocalDate.MAX : deadline).isBefore(LocalDate.now());
    }

}
