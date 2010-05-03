package com.kiva.KivaKatch;

/**
 * Manages the generation of URLs for accessing the Kiva API.
 * 
 * @author Jared Hatfield
 * 
 */
public class KivaAPI
{
    /**
     * 
     * @param name
     * @return
     */
    public static String getLenderURL(String name)
    {
        return "http://api.kivaws.org/v1/lenders/" + name + ".xml";
    }

    /**
     * 
     * @param imageId
     * @return
     */
    public static String getSmallProfileImageURL(String imageId)
    {
        return "http://www.kiva.org/img/w80h80/" + imageId + ".jpg";
    }

    /**
     * 
     * @param lender
     * @return
     */
    public static String getLenderLoansURL(String lender)
    {
        return "http://api.kivaws.org/v1/lenders/" + lender + "/loans.xml";
    }
}
