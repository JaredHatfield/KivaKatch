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
