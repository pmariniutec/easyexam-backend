package com.easyexam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.easyexam")
@SpringBootApplication
public class EasyExamApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyExamApiApplication.class, args);
    }
}
