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
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * The main activity that is displayed when the application launches
 * 
 * @author Jared Hatfield
 * 
 */
public class KivaKatch extends Activity
{
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.i("KivaKatch", "Entered onCreate");
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);

        Button b = (Button) this.findViewById(R.id.Button_ok);
        b.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0)
            {
                // Get the text field's content
                EditText text = (EditText) KivaKatch.this.findViewById(R.id.EditText_username);
                String name = text.getText().toString();

                // Create the new activity and pass in the information
                Intent i = new Intent(KivaKatch.this, DisplayLender.class);
                i.putExtra("name", name);
                KivaKatch.this.startActivity(i);
            }
        });

        Log.i("KivaKatch", "Exited onCreate");
    }
}
