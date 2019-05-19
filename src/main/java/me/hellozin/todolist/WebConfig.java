package me.hellozin.todolist;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TodoInterceptor todoInterceptor;

    public WebConfig(TodoInterceptor todoInterceptor) {
        this.todoInterceptor = todoInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(todoInterceptor)
                .excludePathPatterns("/login");
    }

}
