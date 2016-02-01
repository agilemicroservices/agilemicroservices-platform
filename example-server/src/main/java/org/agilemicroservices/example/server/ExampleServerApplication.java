package org.agilemicroservices.example.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@EnableAutoConfiguration
@ComponentScan(basePackages = "org.agilemicroservices.example.config")
@Configuration
public class ExampleServerApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(ExampleServerApplication.class, args);
    }
}
