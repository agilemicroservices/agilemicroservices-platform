package org.agilemicroservices.example.domain;

import javax.persistence.*;


@Entity
@Access(AccessType.FIELD)
@Table(name = "goodbye")
public class GoodbyeMessage
{
    @Id
    private long id;
    private String content;

    public GoodbyeMessage(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }
}