package org.agilemicroservices.platform.configuration;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.management.MBeanServer;
import java.io.File;
import java.net.URI;


@Configuration
public class ActiveMqConfiguration {

    @Bean
    public ActiveMQServerProperties activeMQServerProperties() {
        return new ActiveMQServerProperties();
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    @Autowired
    public ManagementContext managementContext(MBeanServer mbeanServer) {
        ManagementContext managementContext = new ManagementContext(mbeanServer);
        managementContext.setRmiServerPort(Integer.getInteger("com.sun.management.jmxremote.port", 1099));
        return managementContext;
    }


    @Bean(initMethod = "start", destroyMethod = "stop")
    public BrokerService brokerService(ActiveMQServerProperties activeMQProperties, ManagementContext managementContext)
            throws Exception {
        BrokerService brokerService = new BrokerService();

        brokerService.setUseJmx(true);
        brokerService.setManagementContext(managementContext);

        brokerService.setPersistent(activeMQProperties.isPersistent());
        brokerService.setTmpDataDirectory(new File(activeMQProperties.getDataDirectory() + "/tmp"));
        brokerService.setDataDirectory(activeMQProperties.getDataDirectory());
        brokerService.setVmConnectorURI(new URI("vm://localhost"));

        String[] brokerUrls = activeMQProperties.getBrokerUrls();
        for (String o : brokerUrls) {
            brokerService.addConnector(o);
        }
        return brokerService;
    }

}