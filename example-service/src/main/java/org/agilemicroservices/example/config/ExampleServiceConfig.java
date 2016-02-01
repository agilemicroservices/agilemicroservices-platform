package org.agilemicroservices.example.config;

import org.agilemicroservices.example.service.ExampleService;
import org.agilemicroservices.service.ServiceRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ExampleServiceConfig
{

    @Bean
    public ExampleService exampleService()
    {
        return new ExampleService();
    }

    @Bean
    public RouteBuilder exampleRoute()
    {
        return new ServiceRouteBuilder("exampleService", new Class<?>[]{ExampleService.class});
    }
}