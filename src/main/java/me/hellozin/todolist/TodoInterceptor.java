package me.hellozin.todolist;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Component
public class TodoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        Optional.ofNullable(request.getCookies())
                .map(Arrays::stream)
                .orElseThrow(AuthorNotFoundException::new)
                .filter(cookie -> cookie.getName().equals("author"))
                .findAny()
                .orElseThrow(AuthorNotFoundException::new);

        return true;
    }

}
