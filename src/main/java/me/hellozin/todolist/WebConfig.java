package me.hellozin.todolist;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthorVerificationInterceptor authorVerificationInterceptor;

    public WebConfig(AuthorVerificationInterceptor authorVerificationInterceptor) {
        this.authorVerificationInterceptor = authorVerificationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorVerificationInterceptor)
                .excludePathPatterns("/login");
    }

}
