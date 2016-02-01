package org.agilemicroservices.java2js.mock;

import org.agilemicroservices.java2js.mock2.MockOwner;

import java.math.BigDecimal;


public class MockAccount extends MockBase
{
    private long accountId;
    private String accountNumber;
    private boolean restricted;
    private MockOwner owner;
    private MockOwner[] secondaryOwners;
    private BigDecimal buyingPower;


    public long getAccountId()
    {
        return accountId;
    }

    public void setAccountId(long accountId)
    {
        this.accountId = accountId;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public boolean isRestricted()
    {
        return restricted;
    }

    public void setRestricted(boolean restricted)
    {
        this.restricted = restricted;
    }

    public MockOwner getOwner()
    {
        return owner;
    }

    public void setOwner(MockOwner owner) {
        this.owner = owner;
    }

    public MockOwner[] getSecondaryOwners()
    {
        return secondaryOwners;
    }

    public void setSecondaryOwners(MockOwner[] secondaryOwners)
    {
        this.secondaryOwners = secondaryOwners;
    }

    public BigDecimal getBuyingPower()
    {
        return buyingPower;
    }

    public void setBuyingPower(BigDecimal buyingPower)
    {
        this.buyingPower = buyingPower;
    }
}
