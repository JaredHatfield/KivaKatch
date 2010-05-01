package com.kiva.KivaKatch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

public class DisplayLender extends Activity {
	/** 
     * Called when the activity is first created. 
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.i("KivaKatch", "Entered onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        
        // Display the loading notification
        ProgressDialog dialog = ProgressDialog.show(DisplayLender.this, "", "Loading. Please wait...", true);
        dialog.setCancelable(false);
        dialog.show();
        
        // Get the name from the instance
        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");
        
        // Make the URL
        String url = "http://api.kivaws.org/v1/lenders/" + name + ".xml";
        
        // Load the remote asynchronously
        new DownloadLender().execute(url, this, dialog);
        
    	Log.i("KivaKatch", "Exited onCreate");
    }
}
