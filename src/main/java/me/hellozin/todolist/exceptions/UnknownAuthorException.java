package me.hellozin.todolist.exceptions;

public class UnknownAuthorException extends TodoException {

    public UnknownAuthorException() {
        errorMsg = "Ambiguous author information.";
    }
}
