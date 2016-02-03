package org.agilemicroservices.example.domain;

import java.math.BigDecimal;
import java.util.Date;


public class Account
{
    public long accountId;
    public String accountNumber;
    public Member owner;
    public BigDecimal buyingPower;
    public Date creationDate;
}
