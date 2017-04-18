package org.agilemicroservices.platform.configuration;

import org.springframework.beans.factory.annotation.Value;


public class ActiveMQServerProperties {
    @Value("${activemq.persistent:false}")
    private boolean isPersistent;
    @Value("${activemq.datadir:/tmp/activemq-data}")
    private String dataDirectory;
    @Value("${activemq.broker-urls:ws://0.0.0.0:8188?websocket.maxIdleTime=7200000,tcp://localhost:61613}")
    private String[] brokerUrls;

    public boolean isPersistent() {
        return isPersistent;
    }

    public void setPersistent(boolean persistent) {
        isPersistent = persistent;
    }

    public String getDataDirectory() {
        return dataDirectory;
    }

    public void setDataDirectory(String dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    public String[] getBrokerUrls() {
        return brokerUrls;
    }

    public void setBrokerUrls(String[] brokerUrls) {
        this.brokerUrls = brokerUrls;
    }
}
