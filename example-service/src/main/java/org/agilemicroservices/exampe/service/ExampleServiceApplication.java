package org.agilemicroservices.exampe.service;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ExampleServiceApplication {

    @Bean
    public ExampleService exampleService() {
        return new ExampleService();
    }

    @Bean
    public RouteBuilder exampleRoute() {
        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("activemq:app/hello")
                        .unmarshal().json(JsonLibrary.Jackson, HelloMessage.class)
                        .to("log:org.agilemicroservices?level=INFO&showHeaders=true&multiline=true&showException=true&showStackTrace=true")
                        .to(ExchangePattern.InOut, "bean:exampleService")
                        .marshal().json(JsonLibrary.Jackson);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ExampleServiceApplication.class, args);
    }
}