package me.hellozin.todolist.exceptions;

import lombok.Getter;

@Getter
public class TodoException extends RuntimeException {

    protected String errorMsg;

    public TodoException() {
        this.errorMsg = "TODO List에 문제가 생겼습니다.";
    }
}
