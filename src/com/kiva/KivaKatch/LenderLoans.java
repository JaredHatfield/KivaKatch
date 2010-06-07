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

import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import android.util.Log;
import com.kiva.KivaKatch.Exceptions.LenderLoansCreationException;

/**
 * Represents the loans that a lender has made.
 * 
 * @author Jared Hatfield
 * 
 */
public class LenderLoans
{
    private ArrayList<LenderLoansItem> loans;

    /**
     * 
     * @param name
     * @throws LenderLoansCreationException
     */
    public LenderLoans(String name) throws LenderLoansCreationException
    {
        Log.i("KivaKatch", "Construting a new Lender");
        this.loans = new ArrayList<LenderLoansItem>();

        // Process the parameters
        String url = KivaAPI.getLenderLoansURL(name);

        // Create an instance of HttpClient
        HttpClient client = new DefaultHttpClient();

        // What we are actually requesting
        HttpGet getRequest = new HttpGet(url);

        try
        {
            // Perform the request
            Log.i("KivaKatch", "Requesting: " + url);
            HttpResponse response = client.execute(getRequest);

            // Get the input stream from the response
            InputStream input = response.getEntity().getContent();

            // Build the XML document
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(input);

            // We are concerned with the list of lenders.
            NodeList listOfLoans = doc.getElementsByTagName("loan");

            Log.i("KivaKatch", "Total Loans: " + listOfLoans.getLength());

            // Loop through the list of lenders
            for (int s = 0; s < listOfLoans.getLength(); s++)
            {
                Node loan = listOfLoans.item(s);
                if (loan.getNodeType() == Node.ELEMENT_NODE)
                {
                    LenderLoansItem l = new LenderLoansItem();
                    l.setId(this.getXMLDataFrom(loan, "id"));
                    l.setName(this.getXMLDataFrom(loan, "name"));
                    l.setStatus(this.getXMLDataFrom(loan, "status"));
                    this.loans.add(l);
                }
            }
        }
        catch (Exception e)
        {
            // For now we just log the exception.
            Log.e("KivaKatch", e.getMessage());
            throw new LenderLoansCreationException("LenderLoans could not be retreived", e);
        }
    }

    /**
     * Parses the XML document to retrieve values.
     * 
     * @param node
     *            The Node to parse.
     * @param name
     *            The name of the value to retrieve.
     * @return The string representing the value.
     */
    private String getXMLDataFrom(Node node, String name)
    {
        String result = "";
        try
        {
            Element element = (Element) node;
            NodeList nodeList = element.getElementsByTagName(name);
            Element valueElement = (Element) nodeList.item(0);
            NodeList valueNodeList = valueElement.getChildNodes();
            result = (valueNodeList.item(0)).getNodeValue().trim();
        }
        catch (Exception e)
        {
        }

        return result;
    }
}
