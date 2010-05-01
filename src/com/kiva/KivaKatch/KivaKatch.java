package com.kiva.KivaKatch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class KivaKatch extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.i("KivaKatch", "Entered onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Load the data asynchronously
        new DownloadLender().execute("http://api.kivaws.org/v1/lenders/matt.xml", this);
        
    	Log.i("KivaKatch", "Exited onCreate");
    }
}