package org.agilemicroservices.example.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Access(AccessType.FIELD)
@Table(name = "goodbye")
@XmlRootElement
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