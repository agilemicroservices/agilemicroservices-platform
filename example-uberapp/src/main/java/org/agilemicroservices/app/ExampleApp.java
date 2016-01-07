package org.agilemicroservices.app;

import org.agilemicroservices.platform.configuration.EnableJmsBroker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableJmsBroker
@SpringBootApplication
public class ExampleApp {

    public static void main(String[] args) {
        SpringApplication.run(ExampleApp.class, args);
    }
}