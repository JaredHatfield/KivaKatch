/*
 * KivaKatch is an Android application for accessing Kiva.org
 * Copyright (C) 2010  Jared Hatfield
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
