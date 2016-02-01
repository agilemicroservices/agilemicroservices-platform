package org.agilemicroservices.example.domain;

import javax.persistence.*;


@Entity
@Access(AccessType.FIELD)
@Table(name = "hello")
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
