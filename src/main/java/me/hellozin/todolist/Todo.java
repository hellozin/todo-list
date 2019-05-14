package me.hellozin.todolist;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

}
