package com.example.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ComponentScan(basePackages = {"com.example"})
public class MsmServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsmServiceApplication.class, args);
    }

    @GetMapping("/")
    public String msm(){
        return "Hello MSM";
    }
}
