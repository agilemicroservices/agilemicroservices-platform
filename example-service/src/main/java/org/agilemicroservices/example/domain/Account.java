package org.agilemicroservices.example.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.Date;


@XmlRootElement
public class Account
{
    public long accountId;
    public String accountNumber;
    public Member owner;
    public BigDecimal buyingPower;
    public Date creationDate;
}
