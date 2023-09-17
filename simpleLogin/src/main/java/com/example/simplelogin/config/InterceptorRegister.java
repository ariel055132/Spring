package com.example.simplelogin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

public class InterceptorRegister implements WebMvcConfigurer {
    @Bean
    public HandlerInterceptor getInterceptor() {
        return new UserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> pathPatterns = new ArrayList<>();
        pathPatterns.add("/api/user/update");
        registry.addInterceptor(getInterceptor()).addPathPatterns(pathPatterns);
    }
}
