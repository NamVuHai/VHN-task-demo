package com.assignment.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.assignment.core",
        "com.assignment.auth"
})
public class AssignmentAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssignmentAuthApplication.class, args);
    }

}
