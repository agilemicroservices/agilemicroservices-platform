package org.agilemicroservices.service;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.lang.reflect.Method;


/**
 * Builds routes for services by mapping their interface(s) to queues based on the convention below.
 * <ul>
 * <li>Method must be declared by the interface, not inherited.</li>
 * <li>Method must have a single parameter.</li>
 * <li>Queue name is equal to serviceName/methodName.</li>
 * <li>Message's JMSReplyTo value used to route response.</li>
 * </ul>
 */
public class ServiceRouteBuilder extends RouteBuilder
{
    private String serviceName;
    private Class<?>[] serviceInterfaces;


    /**
     * Creates a new fully configured instance for connecting the named service and interface to the broker.
     *
     * @param serviceName       the name of the service, as defined in the Spring context.
     * @param serviceInterfaces the interfaces defining the service's remotely accessible methods.
     */
    public ServiceRouteBuilder(String serviceName, Class<?>[] serviceInterfaces)
    {
        this.serviceName = serviceName;
        this.serviceInterfaces = serviceInterfaces;
    }


    @Override
    public void configure() throws Exception
    {
        for (Class<?> i : serviceInterfaces)
        {
            configureForInterface(i);
        }
    }

    private void configureForInterface(Class<?> serviceInterface)
    {
        Class<?> parameterType;

        // must only be defined once, prior to any other route created by the builder
        onException(Exception.class)
                .to("log:org.agilemicroservices?level=ERROR&showHeaders=true&multiline=true&showException=true&showStackTrace=true");

        for (Method o : serviceInterface.getMethods())
        {
            if (o.getDeclaringClass() == Object.class)
            {
                continue;
            }

            parameterType = findMessageParameterType(o);
            if (parameterType != null)
            {
                String requestQueueName = formatQueueName(o.getName());
                from("activemq:" + requestQueueName)
                        .unmarshal().json(JsonLibrary.Jackson, parameterType)
                        .to("log:org.agilemicroservices?level=INFO&showHeaders=true&multiline=true&showException=true&showStackTrace=true")
                        .to(ExchangePattern.InOut, "bean:" + serviceName)
                        .marshal().json(JsonLibrary.Jackson);
            }
        }
    }

    private Class<?> findMessageParameterType(Method method)
    {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Class<?> messageParameterType = null;
        if (parameterTypes.length == 1)
        {
            messageParameterType = parameterTypes[0];
        }
        return messageParameterType;
    }

    private String formatQueueName(String name)
    {
        return serviceName.toLowerCase() + "_" + name.toLowerCase();
    }
}