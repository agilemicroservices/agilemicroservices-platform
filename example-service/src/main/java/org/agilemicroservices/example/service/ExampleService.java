package org.agilemicroservices.example.service;


import org.agilemicroservices.example.domain.GoodbyeMessage;
import org.agilemicroservices.example.domain.HelloMessage;

public class ExampleService {

    public GoodbyeMessage execute(HelloMessage message) {
        return new GoodbyeMessage("Goodbye " + message.getName());
    }
}