package org.agilemicroservices.example.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Access(AccessType.FIELD)
@Table(name = "hello")
@XmlRootElement
public class HelloMessage
{
    @Id
    private long id;
    private String name;


    public HelloMessage()
    {
    }

    public HelloMessage(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "[id:" + id + ",name:" + name + "]";
    }
}
