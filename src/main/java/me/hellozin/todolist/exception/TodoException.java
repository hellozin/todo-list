package me.hellozin.todolist.exception;

import lombok.Getter;

@Getter
public class TodoException extends RuntimeException {

    protected String errorMsg;

    public TodoException() {
        this.errorMsg = "Something wrong with TODO List.";
    }
}
