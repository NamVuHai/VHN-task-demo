package com.assignment.task.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
        "com.assignment.core",
        "com.assignment.task.manager"
})
@EnableFeignClients
public class AssigmentTaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssigmentTaskManagerApplication.class, args);
    }

}
