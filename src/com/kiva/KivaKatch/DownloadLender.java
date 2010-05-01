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
	        		// Mark down the information retreived from the XML document.
	        		kiva.setLenderId(getXMLDataFrom(firstPersonNode, "lender_id"));
	        		kiva.setName(getXMLDataFrom(firstPersonNode, "name"));
	                kiva.setWhereabouts(getXMLDataFrom(firstPersonNode, "whereabouts"));
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
    	}
    	else{
    		Log.i("KivaKatch", "No Data Loaded opnPostExecute");
    	}
	    
	    Log.i("KivaKatch", "Exited onPostExecute");
    }
    
    /**
     * Parses the XML document to retrieve values.
     * @param node The Node to parse.
     * @param name The name of the value to retrieve.
     * @return The string representing the value.
     */
    private String getXMLDataFrom(Node node, String name){
		Element element = (Element)node;
		NodeList nodeList = element.getElementsByTagName(name);
        Element valueElement = (Element)nodeList.item(0);
        NodeList valueNodeList = valueElement.getChildNodes();
        String result = ((Node)valueNodeList.item(0)).getNodeValue().trim();
        return result;
	}
}
