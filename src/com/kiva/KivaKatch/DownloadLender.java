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
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import com.kiva.KivaKatch.Information.Lenders;

public class DownloadLender extends AsyncTask<Object, Object, Lenders> {
	
	/**
	 * The activity that called this AsyncTask.
	 */
	private Activity activity;
	
	/**
	 * The progress dialog that is displayed on the screen.
	 */
	private ProgressDialog dialog;
	
	/**
	 * A flag to indicate the success or failure of the web request and parsing.
	 */
	private Boolean success;
	
	/**
	 * The request for remote resource and parsing of the result.
	 */
	protected Lenders doInBackground(Object... params) {
		// Process the parameters
		String url = (String)params[0];
		this.activity = (Activity)params[1];
		this.dialog = (ProgressDialog)params[2];
		
		// We assume this operation fails until we reach the end.
		this.success = false;
		
		Log.i("KivaKatch", "Entered doInBackground");
		// Create an instance of HttpClient
        HttpClient client = new DefaultHttpClient();
        
        // What we are actually requesting
        HttpGet getRequest = new HttpGet(url);
        Lenders kiva = new Lenders();
        
        try{
        	// Perform the request
        	HttpResponse response  = client.execute(getRequest);
	        
        	// Get the input stream from the response
	        InputStream input = response.getEntity().getContent();
	        
	        // Build the XML document
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        Document doc = db.parse(input);
	        
	        // We are concerned with the list of lenders.
	        NodeList listOfLenders = doc.getElementsByTagName("lender");
	        
	        // Loop through the list of lenders
	        for(int s=0; s<listOfLenders.getLength() ; s++){
	        	Node firstPersonNode = listOfLenders.item(s);
	        	if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE){
	        		// Mark down the information retrieved from the XML document.
	        		kiva.setLenderId(getXMLDataFrom(firstPersonNode, "lender_id"));
	        		kiva.setName(getXMLDataFrom(firstPersonNode, "name"));
	                kiva.setWhereabouts(getXMLDataFrom(firstPersonNode, "whereabouts"));
	                kiva.setCountryCode(getXMLDataFrom(firstPersonNode, "country_code"));
	                kiva.setUid(getXMLDataFrom(firstPersonNode, "uid"));
	                kiva.setMemberSince(getXMLDataFrom(firstPersonNode, "member_since"));
	                kiva.setPersonalUrl(getXMLDataFrom(firstPersonNode, "personal_url"));
	                kiva.setOccupation(getXMLDataFrom(firstPersonNode, "occupation"));
	                kiva.setLoanBecause(getXMLDataFrom(firstPersonNode, "loan_because"));
	                kiva.setOccupationalInfo(getXMLDataFrom(firstPersonNode, "occupational_info"));
	                kiva.setLoanCount(getXMLDataFrom(firstPersonNode, "loan_count"));
	                kiva.setInviteeCount(getXMLDataFrom(firstPersonNode, "invitee_count"));
	                break;
	        	}
	        }
	        
	        // Now that we have made it through all of that code, we know it was a success.
	        this.success = true;
        }
        catch(Exception e){
        	// For now we just log the exception.
        	Log.e("KivaKatch", e.getMessage());
        	
        	// TODO: Change the flow of the application based on the exception that was thrown.
        }
        
        Log.i("KivaKatch", "Exited doInBackground");
        return kiva;
    }

    /**
     * Process the response from the server and update the Gui.
     * @param result The Lender information.
     */
    protected void onPostExecute(Lenders result) {
    	Log.i("KivaKatch", "Entered onPostExecute");
	 
    	if(this.success){
    		// The remote information was retrieved successfully, update the Gui
		 	TextView lender = (TextView)this.activity.findViewById(R.id.Text_lender_id);
		    lender.setText(result.getLenderId());
		    
		    TextView name = (TextView)this.activity.findViewById(R.id.Text_name);
		    name.setText(result.getName());
		    
		    TextView whereabouts = (TextView)this.activity.findViewById(R.id.Text_whereabouts);
		    whereabouts.setText(result.getWhereabouts());
		    
		    TextView country_code = (TextView)this.activity.findViewById(R.id.Text_country_code);
		    country_code.setText(result.getCountryCode());
		    
		    TextView uid = (TextView)this.activity.findViewById(R.id.Text_uid);
		    uid.setText(result.getUid());
		    
		    TextView member_since = (TextView)this.activity.findViewById(R.id.Text_member_since);
		    member_since.setText(result.getMemberSince());
		    
		    TextView personal_url = (TextView)this.activity.findViewById(R.id.Text_personal_url);
		    personal_url.setText(result.getPersonalUrl());
		    
		    TextView occupation = (TextView)this.activity.findViewById(R.id.Text_occupation);
		    occupation.setText(result.getOccupation());
		    
		    TextView loan_because = (TextView)this.activity.findViewById(R.id.Text_loan_because);
		    loan_because.setText(result.getLoanBecause());
		    
		    TextView occupational_info = (TextView)this.activity.findViewById(R.id.Text_occupational_info);
		    occupational_info.setText(result.getOccupationalInfo());
		    
		    TextView loan_count = (TextView)this.activity.findViewById(R.id.Text_loan_count);
		    loan_count.setText(result.getLoanCount());
		    
		    TextView invitee_count = (TextView)this.activity.findViewById(R.id.Text_invitee_count);
		    invitee_count.setText(result.getInviteeCount());
    	}
    	else{
    		Log.i("KivaKatch", "No Data Loaded opnPostExecute");
    	}
    	
    	// Since we are done loading remove the progress dialog.
    	this.dialog.cancel();
	    
	    Log.i("KivaKatch", "Exited onPostExecute");
    }
    
    /**
     * Parses the XML document to retrieve values.
     * @param node The Node to parse.
     * @param name The name of the value to retrieve.
     * @return The string representing the value.
     */
    private String getXMLDataFrom(Node node, String name){
    	String result = "";
    	try{
			Element element = (Element)node;
			NodeList nodeList = element.getElementsByTagName(name);
	        Element valueElement = (Element)nodeList.item(0);
	        NodeList valueNodeList = valueElement.getChildNodes();
	        result = ((Node)valueNodeList.item(0)).getNodeValue().trim();
    	}
    	catch(Exception e){
    	}
    	
    	return result;
	}
}