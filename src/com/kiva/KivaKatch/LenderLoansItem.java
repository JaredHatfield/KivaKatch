package com.kiva.KivaKatch;

/**
 * A single record for a Lender Loan.
 * 
 * @author Jared Hatfield
 * 
 */
public class LenderLoansItem
{
    private String id;

    private String name;

    private String status;

    public LenderLoansItem()
    {
        this.id = "";
        this.name = "";
        this.status = "";
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getStatus()
    {
        return this.status;
    }
}
