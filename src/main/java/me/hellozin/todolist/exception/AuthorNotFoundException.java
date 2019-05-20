package me.hellozin.todolist.exception;

public class AuthorNotFoundException extends TodoException {

    public AuthorNotFoundException() {
        errorMsg = "Author not found.";
    }
}
