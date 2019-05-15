package me.hellozin.todolist;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Setter
@Getter
@ToString
public class Todo {

    @Id
    @GeneratedValue
    private long id;

    private String author;

    private String title;

    private String content;

    @Min(1)
    @Max(5)
    private int importance;

}
