package org.agilemicroservices.broker.activemq;

import org.agilemicroservices.platform.configuration.EnableJmsBroker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;


@EnableJmsBroker
@SpringBootApplication(exclude = EmbeddedServletContainerAutoConfiguration.class)
public class BrokerApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BrokerApplication.class);
        app.setWebEnvironment(false);
        app.run(args);
    }
}
