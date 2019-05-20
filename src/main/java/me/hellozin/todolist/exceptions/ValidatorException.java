package me.hellozin.todolist.exceptions;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidatorException extends TodoException {

    public ValidatorException(BindingResult bindingResult) {
        StringBuilder builder = new StringBuilder();
        for (FieldError error : bindingResult.getFieldErrors()) {
            builder.append('[');
            builder.append(error.getField());
            builder.append(']');
            builder.append(error.getDefaultMessage());
            builder.append('\n');
        }
        errorMsg = builder.toString();
    }

    @Override
    public String getErrorMsg() {
        return super.getErrorMsg();
    }
}
