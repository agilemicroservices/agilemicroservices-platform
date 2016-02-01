package org.agilemicroservices.java2js.mock2;


import java.util.Date;

public class MockOwner
{
    private long id;
    private String fullName;
    private String[] phoneNumbers;
    private Date dob;


    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String[] getPhoneNumbers()
    {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String[] phoneNumbers)
    {
        this.phoneNumbers = phoneNumbers;
    }

    public Date getDob()
    {
        return dob;
    }

    public void setDob(Date dob)
    {
        this.dob = dob;
    }
}