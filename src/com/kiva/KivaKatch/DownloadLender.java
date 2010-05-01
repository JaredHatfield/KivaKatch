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
import android.view.View;
import android.widget.TextView;
import com.kiva.KivaKatch.Information.Lenders;

public class DownloadLender extends AsyncTask<Object, Object, Lenders> {
	
	private Activity activity;
	
	private Boolean success;
	
	protected Lenders doInBackground(Object... params) {
		String url = (String)params[0];
		this.activity = (Activity)params[1];
		
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
	        
	        // XML STUFF!
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        Document doc = db.parse(input);
	        
	        // Now we can parse the XML document!
	        NodeList listOfLenders = doc.getElementsByTagName("lender");
	        
	        for(int s=0; s<listOfLenders.getLength() ; s++){
	        	Node firstPersonNode = listOfLenders.item(s);
	        	if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE){
	        		kiva.setLenderId(getXMLDataFrom(firstPersonNode, "lender_id"));
	        		
	        		kiva.setName(getXMLDataFrom(firstPersonNode, "name"));
	        		
	                kiva.setWhereabouts(getXMLDataFrom(firstPersonNode, "whereabouts"));
	        	}
	        }
	        
	        this.success = true;
        }
        catch(Exception e){
        	Log.e("KivaKatch", e.getMessage());
        }
        
        Log.i("KivaKatch", "Exited doInBackground");
        return kiva;
    }

    /**
     * This is where we should update the GUI!
     * @param result
     */
    protected void onPostExecute(Lenders result) {
    	Log.i("KivaKatch", "Entered onPostExecute");
	 
    	if(this.success){
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
    
    private String getXMLDataFrom(Node node, String name){
    	// TODO: All of these variable names are stupid!
		Element firstPersonElement = (Element)node;
		NodeList firstNameList = firstPersonElement.getElementsByTagName(name);
        Element firstNameElement = (Element)firstNameList.item(0);

        NodeList textFNList = firstNameElement.getChildNodes();
        String strLender = ((Node)textFNList.item(0)).getNodeValue().trim();
        return strLender;
	}
}
