package com.example.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@ComponentScan(basePackages = {"com.example"})
public class EduServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(EduServiceApplication.class, args);
    }

    @GetMapping("/")
    public String hello() {
        return "Hello";
    }
}
