package me.hellozin.todolist;

public class AuthorNotFoundException extends TodoException {

    public AuthorNotFoundException() {
        errorMsg = "Author not found.";
    }
}
