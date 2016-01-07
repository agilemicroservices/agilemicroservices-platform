package org.agilemicroservices.example.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;


@SpringBootApplication
public class ExampleWebappApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleWebappApplication.class, args);
    }
}
