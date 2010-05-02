package com.kiva.KivaKatch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

/**
 * The activity for displaying a lender's information
 * 
 * @author Jared Hatfield
 * 
 */
public class DisplayLender extends Activity
{
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.i("KivaKatch", "Entered onCreate");
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.profile);

        // Display the loading notification
        ProgressDialog dialog = ProgressDialog.show(DisplayLender.this, "", "Loading. Please wait...", true);
        dialog.setCancelable(false);
        dialog.show();

        // Get the name from the instance
        Bundle extras = this.getIntent().getExtras();
        String name = extras.getString("name");

        // Load the remote asynchronously
        new DownloadLender().execute(name, this, dialog);

        Log.i("KivaKatch", "Exited onCreate");
    }
}
