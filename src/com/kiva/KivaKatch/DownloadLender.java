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
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.kiva.KivaKatch.Exceptions.LenderCreationException;
import com.kiva.KivaKatch.Exceptions.ProfilePictureCreationException;

/**
 * The asynchronous task for downloading lender information
 * 
 * @author Jared Hatfield
 * 
 */
public class DownloadLender extends AsyncTask<Object, Object, Lender>
{

    /**
     * The activity that called this AsyncTask.
     */
    private Activity activity;

    /**
     * The progress dialog that is being displayed as part of this activity.
     */
    private ProgressDialog dialog;

    /**
     * The profile picture for the user.
     */
    private Bitmap profile;

    /**
     * The request for remote resource and parsing of the result.
     */
    @Override
    protected Lender doInBackground(Object... params)
    {
        // The name of the person being requested
        String name = (String) params[0];

        // Remember the activity, we will need it later
        this.activity = (Activity) params[1];

        // Remember the progress dialog
        this.dialog = (ProgressDialog) params[2];

        // The controller that is used to request resources
        ResourceController c = ResourceController.Instance();

        // The instance of the lender
        Lender lender = null;

        // Retrieve the lender
        try
        {
            lender = c.getLender(name);
        }
        catch (LenderCreationException e)
        {
            Log.e("KivaKatch", e.getMessage());
        }

        // Retrieve the profile picture
        if (lender != null)
        {
            try
            {
                this.profile = c.getProfilePicture(lender.getImageId());
            }
            catch (ProfilePictureCreationException e)
            {
                Log.e("KivaKatch", e.getMessage());
            }
        }

        return lender;
    }

    /**
     * Process the response from the server and update the Gui.
     * 
     * @param result
     *            The Lender information.
     */
    @Override
    protected void onPostExecute(Lender result)
    {
        Log.i("KivaKatch", "Entered onPostExecute");

        // The remote information was retrieved successfully, update the Gui
        if (result != null)
        {
            TextView lender = (TextView) this.activity.findViewById(R.id.Text_lender_id);
            lender.setText(result.getLenderId());

            TextView name = (TextView) this.activity.findViewById(R.id.Text_name);
            name.setText(result.getName());

            TextView whereabouts = (TextView) this.activity.findViewById(R.id.Text_whereabouts);
            whereabouts.setText(result.getWhereabouts());

            TextView country_code = (TextView) this.activity.findViewById(R.id.Text_country_code);
            country_code.setText(result.getCountryCode());

            TextView uid = (TextView) this.activity.findViewById(R.id.Text_uid);
            uid.setText(result.getUid());

            TextView member_since = (TextView) this.activity.findViewById(R.id.Text_member_since);
            member_since.setText(result.getMemberSince());

            TextView personal_url = (TextView) this.activity.findViewById(R.id.Text_personal_url);
            personal_url.setText(result.getPersonalUrl());

            TextView occupation = (TextView) this.activity.findViewById(R.id.Text_occupation);
            occupation.setText(result.getOccupation());

            TextView loan_because = (TextView) this.activity.findViewById(R.id.Text_loan_because);
            loan_because.setText(result.getLoanBecause());

            TextView occupational_info = (TextView) this.activity.findViewById(R.id.Text_occupational_info);
            occupational_info.setText(result.getOccupationalInfo());

            TextView loan_count = (TextView) this.activity.findViewById(R.id.Text_loan_count);
            loan_count.setText(result.getLoanCount());

            TextView invitee_count = (TextView) this.activity.findViewById(R.id.Text_invitee_count);
            invitee_count.setText(result.getInviteeCount());

            if (this.profile != null)
            {
                ImageView profile = (ImageView) this.activity.findViewById(R.id.ImageView_profile);
                profile.setImageBitmap(this.profile);
            }
        }

        // Remove the dialog.
        this.dialog.cancel();

        Log.i("KivaKatch", "Exited onPostExecute");
    }
}
