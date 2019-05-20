package me.hellozin.todolist.exception;

import me.hellozin.todolist.Todo;
import org.springframework.validation.BindingResult;

public class ValidatorException extends TodoException {

    public ValidatorException(BindingResult bindingResult) {
        errorMsg = bindingResult.getAllErrors().toString();
        Todo notValidatedTodo = (Todo) bindingResult.getTarget();
        System.out.println(notValidatedTodo);
    }

    @Override
    public String getErrorMsg() {
        return super.getErrorMsg();
    }
}
