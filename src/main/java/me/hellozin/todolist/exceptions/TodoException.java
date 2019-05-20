package me.hellozin.todolist.exceptions;

import lombok.Getter;

@Getter
public class TodoException extends RuntimeException {

    protected String errorMsg;

    public TodoException() {
        this.errorMsg = "Error occurred with TODO List.";
    }
}
