package com.kiva.KivaKatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class KivaKatch extends Activity {
    /** 
     * Called when the activity is first created. 
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.i("KivaKatch", "Entered onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Load the remote asynchronously
        //new DownloadLender().execute("http://api.kivaws.org/v1/lenders/matt.xml", this);
        
        Button b = (Button) findViewById(R.id.Button_ok);
        b.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View arg0) {
        		// Get the text field's content
        		EditText text = (EditText)findViewById(R.id.EditText_username);
        		String name = text.getText().toString();
        		
        		// Create the new activity and pass in the information
        		Intent i = new Intent(KivaKatch.this, DisplayLender.class);
        		i.putExtra("name", name);
        		startActivity(i);
    		} 
    	});
        
    	Log.i("KivaKatch", "Exited onCreate");
    }
}