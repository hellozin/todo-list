package me.hellozin.todolist;

import me.hellozin.todolist.exceptions.UnknownAuthorException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Component
public class AuthorVerificationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        Optional.ofNullable(request.getCookies())
                .map(Arrays::stream)
                .orElseThrow(UnknownAuthorException::new)
                .filter(cookie -> cookie.getName().equals("currentUser"))
                .findAny()
                .orElseThrow(UnknownAuthorException::new);

        return true;
    }

}
