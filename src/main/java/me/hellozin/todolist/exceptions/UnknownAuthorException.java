package me.hellozin.todolist.exceptions;

public class UnknownAuthorException extends TodoException {

    public UnknownAuthorException() {
        this.errorMsg = "사용자 정보가 올바르지 않습니다.";
    }
}
