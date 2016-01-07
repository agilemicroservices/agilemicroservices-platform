package org.agilemicroservices.exampe.service;


public class ExampleService {

    public GoodbyeMessage execute(HelloMessage message) {
        return new GoodbyeMessage("Goodbye " + message.getName());
    }
}