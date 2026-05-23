package com.rent.hatanotifier.web.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class AppConfig {


    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    private static int defaultPageSize;
    private static int defaultPageNumber;

    @Value("${default.page.size}")
    private int defaultPageSizeNonStatic;

    @Value("${default.page.number}")
    private int defaultPageNumberNonStatic;

    @PostConstruct
    private void init() {
        defaultPageSize = defaultPageSizeNonStatic;
        defaultPageNumber = defaultPageNumberNonStatic;
    }

    public static int getDefaultPageSize() {
        return defaultPageSize;
    }

    public static int getDefaultPageNumber() {
        return defaultPageNumber;
    }
}
