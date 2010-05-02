package com.kiva.KivaKatch;

import java.io.InputStream;
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
import com.kiva.KivaKatch.Exceptions.LenderCreationException;

/**
 * Represents the lender information.
 * 
 * @author Jared Hatfield
 * 
 */
public class Lenders
{
    /**
     * The base url used to access the Kiva API.
     */
    private static String baseUrl = "http://api.kivaws.org/v1/lenders/<NAME>.xml";

    /**
     * The lender_id.
     */
    private String lender_id;

    /**
     * The name.
     */
    private String name;

    /**
     * The image id.
     */
    private String image_id;

    /**
     * The whereabouts.
     */
    private String whereabouts;

    /**
     * The country_code.
     */
    private String country_code;

    /**
     * The uid.
     */
    private String uid;

    /**
     * The member_since.
     */
    private String member_since;

    /**
     * The personal_url.
     */
    private String personal_url;

    /**
     * The occupation.
     */
    private String occupation;

    /**
     * The loan_because.
     */
    private String loan_because;

    /**
     * The occupational_info.
     */
    private String occupational_info;

    /**
     * The loan_count.
     */
    private String loan_count;

    /**
     * The invitee_count.
     */
    private String invitee_count;

    /**
     * 
     * @param name
     * @throws LenderCreationException
     */
    public Lenders(String name) throws LenderCreationException
    {
        Log.i("KivaKatch", "Construting a new Lender");

        // Process the parameters
        String url = Lenders.baseUrl.replaceFirst("<NAME>", name);

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
            NodeList listOfLenders = doc.getElementsByTagName("lender");

            // Loop through the list of lenders
            for (int s = 0; s < listOfLenders.getLength(); s++)
            {
                Node firstPersonNode = listOfLenders.item(s);
                if (firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    // Mark down the information retrieved from the XML
                    // document.
                    this.setLenderId(this.getXMLDataFrom(firstPersonNode, "lender_id"));
                    this.setName(this.getXMLDataFrom(firstPersonNode, "name"));
                    this.setImageId(this.getImageId(firstPersonNode));
                    this.setWhereabouts(this.getXMLDataFrom(firstPersonNode, "whereabouts"));
                    this.setCountryCode(this.getXMLDataFrom(firstPersonNode, "country_code"));
                    this.setUid(this.getXMLDataFrom(firstPersonNode, "uid"));
                    this.setMemberSince(this.getXMLDataFrom(firstPersonNode, "member_since"));
                    this.setPersonalUrl(this.getXMLDataFrom(firstPersonNode, "personal_url"));
                    this.setOccupation(this.getXMLDataFrom(firstPersonNode, "occupation"));
                    this.setLoanBecause(this.getXMLDataFrom(firstPersonNode, "loan_because"));
                    this.setOccupationalInfo(this.getXMLDataFrom(firstPersonNode, "occupational_info"));
                    this.setLoanCount(this.getXMLDataFrom(firstPersonNode, "loan_count"));
                    this.setInviteeCount(this.getXMLDataFrom(firstPersonNode, "invitee_count"));
                    break;
                }
            }
        }
        catch (Exception e)
        {
            // For now we just log the exception.
            Log.e("KivaKatch", e.getMessage());
            throw new LenderCreationException("Lender could not be retreived", e);
        }
    }

    /**
     * Sets the value of lender_id.
     * 
     * @param lender_id
     *            The new lender_id.
     */
    private void setLenderId(String lender_id)
    {
        this.lender_id = lender_id;
    }

    /**
     * Sets the value of name.
     * 
     * @param name
     *            The new name.
     */
    private void setName(String name)
    {
        this.name = name;
    }

    /**
     * Sets the value of image_id.
     * 
     * @param image_id
     *            The new image_id.
     */
    private void setImageId(String image_id)
    {
        this.image_id = image_id;
    }

    /**
     * Sets the value of whereabouts.
     * 
     * @param whereabouts
     *            The new whereabouts.
     */
    private void setWhereabouts(String whereabouts)
    {
        this.whereabouts = whereabouts;
    }

    /**
     * Sets the value of country_code.
     * 
     * @param country_code
     *            The new country_code.
     */
    private void setCountryCode(String country_code)
    {
        this.country_code = country_code;
    }

    /**
     * Sets the value of uid.
     * 
     * @param uid
     *            The new uid.
     */
    private void setUid(String uid)
    {
        this.uid = uid;
    }

    /**
     * Sets the value of member_since.
     * 
     * @param member_since
     *            The new member_since.
     */
    private void setMemberSince(String member_since)
    {
        this.member_since = member_since;
    }

    /**
     * Sets the value of personal_url.
     * 
     * @param personal_url
     *            The new personal_url.
     */
    private void setPersonalUrl(String personal_url)
    {
        this.personal_url = personal_url;
    }

    /**
     * Sets the value of occupation.
     * 
     * @param occupation
     *            The new occupation.
     */
    private void setOccupation(String occupation)
    {
        this.occupation = occupation;
    }

    /**
     * Sets the value of loan_because.
     * 
     * @param loan_because
     *            The new loan_because.
     */
    private void setLoanBecause(String loan_because)
    {
        this.loan_because = loan_because;
    }

    /**
     * Sets the value of occupational_info.
     * 
     * @param occupational_info
     *            The new occupational_info.
     */
    private void setOccupationalInfo(String occupational_info)
    {
        this.occupational_info = occupational_info;
    }

    /**
     * Sets the value of loan_count.
     * 
     * @param loan_count
     *            The new loan_count.
     */
    private void setLoanCount(String loan_count)
    {
        this.loan_count = loan_count;
    }

    /**
     * Sets the value of invitee_count.
     * 
     * @param invitee_count
     *            The new invitee_count.
     */
    private void setInviteeCount(String invitee_count)
    {
        this.invitee_count = invitee_count;
    }

    /**
     * Gets the value of lender_id.
     * 
     * @return The lender_id.
     */
    public String getLenderId()
    {
        return this.lender_id;
    }

    /**
     * Gets the value of name.
     * 
     * @return The name.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Gets the value of image_id.
     * 
     * @return The image_id.
     */
    public String getImageId()
    {
        return this.image_id;
    }

    /**
     * Gets the value of whereabouts.
     * 
     * @return The whereabouts.
     */
    public String getWhereabouts()
    {
        return this.whereabouts;
    }

    /**
     * Gets the value of country_code.
     * 
     * @return The country_code.
     */
    public String getCountryCode()
    {
        return this.country_code;
    }

    /**
     * Gets the value of uid.
     * 
     * @return The uid.
     */
    public String getUid()
    {
        return this.uid;
    }

    /**
     * Gets the value of member_since.
     * 
     * @return The member_since.
     */
    public String getMemberSince()
    {
        return this.member_since;
    }

    /**
     * Gets the value of personal_url.
     * 
     * @return The personal_url.
     */
    public String getPersonalUrl()
    {
        return this.personal_url;
    }

    /**
     * Gets the value of occupation.
     * 
     * @return The occupation.
     */
    public String getOccupation()
    {
        return this.occupation;
    }

    /**
     * Gets the value of loan_because.
     * 
     * @return The loan_because.
     */
    public String getLoanBecause()
    {
        return this.loan_because;
    }

    /**
     * Gets the value of occupational_info.
     * 
     * @return The occupational_info.
     */
    public String getOccupationalInfo()
    {
        return this.occupational_info;
    }

    /**
     * Gets the value of loan_count.
     * 
     * @return The loan_count.
     */
    public String getLoanCount()
    {
        return this.loan_count;
    }

    /**
     * Gets the value of invitee_count.
     * 
     * @return The invitee_count.
     */
    public String getInviteeCount()
    {
        return this.invitee_count;
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

    /**
     * Parses the XML and returns the image id for the lender.
     * 
     * @param node
     *            The lender XML tree to parse.
     * @return The string representation of the picture id.
     */
    private String getImageId(Node node)
    {
        String result = "";
        try
        {
            Element element = (Element) node;
            NodeList nodeList = element.getElementsByTagName("image");
            Element valueElement = (Element) nodeList.item(0);
            NodeList nodeList2 = valueElement.getElementsByTagName("id");
            Element valueElement2 = (Element) nodeList2.item(0);
            NodeList valueNodeList = valueElement2.getChildNodes();
            result = (valueNodeList.item(0)).getNodeValue().trim();
        }
        catch (Exception e)
        {
        }

        return result;
    }
}
