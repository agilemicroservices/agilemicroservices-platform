package org.agilemicroservices.example.webapp;

import org.agilemicroservices.java2js.servlet.Java2JsServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;


@SpringBootApplication
public class ExampleWebappApplication {

    @Bean
    public ServletRegistrationBean codeGenerationServlet()
    {
        ServletRegistrationBean bean = new ServletRegistrationBean(new Java2JsServlet(), "/domain.js");
        bean.addInitParameter(Java2JsServlet.ALLOWED_PACKAGE_NAMES_PARAM, "org.agilemicroservices,org.agilemicroservices.example.domain");
        return bean;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExampleWebappApplication.class, args);
    }
}
