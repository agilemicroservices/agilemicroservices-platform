package org.agilemicroservices.example.domain;


public class Member
{
    private long memberId;
    private String fullName;


    public long getMemberId()
    {
        return memberId;
    }

    public void setMemberId(long memberId)
    {
        this.memberId = memberId;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
}
