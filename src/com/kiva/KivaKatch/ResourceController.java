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

import java.io.BufferedInputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.kiva.KivaKatch.Exceptions.LenderCreationException;
import com.kiva.KivaKatch.Exceptions.ProfilePictureCreationException;

/**
 * The singleton controller for requesting resources from the API.
 * 
 * @author Jared Hatfield
 * 
 */
public class ResourceController
{
    /**
     * The instance of ResourceController.
     */
    private static ResourceController instance;

    /**
     * The list of Lenders that have been retrieved. This is a soft reference
     * map so this buffer can be cleared at any time.
     */
    private Map<String, SoftReference<Lender>> lenders;

    /**
     * The list of profile pictures that have been retreived. This is a soft
     * reference map so this buffer can be cleared at any time.
     */
    private Map<String, SoftReference<Bitmap>> profilePicture;

    /**
     * Private constructor for ResourceController
     */
    private ResourceController()
    {
        this.lenders = new HashMap<String, SoftReference<Lender>>();
        this.profilePicture = new HashMap<String, SoftReference<Bitmap>>();
    }

    /**
     * Returns a singleton instance of ResourceController
     * 
     * @return An instance of ResourceController
     */
    public static ResourceController Instance()
    {
        if (ResourceController.instance == null)
        {
            ResourceController.instance = new ResourceController();
        }

        return ResourceController.instance;
    }

    /**
     * Retrieves a lender.
     * 
     * @param name
     *            The name of the lender.
     * @return The lender.
     * @throws LenderCreationException
     */
    public Lender getLender(String name) throws LenderCreationException
    {
        SoftReference<Lender> soft = this.lenders.get(name);

        // Check to see if the item was in the dictionary
        if (soft != null)
        {
            Lender lender = soft.get();

            // Check to see if the object was not garbage collected
            if (lender != null)
            {
                // We found the object in the cache, we are done!
                Log.i("KivaKatch", "Lender located in the cache");
                return lender;
            }
            else
            {
                // Remove the entry in the Map, we will add it back in the next
                // step.
                this.lenders.remove(name);
            }
        }

        // Since we don't have the data in our cache, we must fetch it
        Log.i("KivaKatch", "Lender NOT located in the cache");
        Lender l = new Lender(name);
        this.lenders.put(name, new SoftReference<Lender>(l));
        return l;
    }

    /**
     * Retrieve a profile picture.
     * 
     * @param imageId
     *            The id of the picture to retrieve.
     * @return The bitmap image requested.
     * @throws ProfilePictureCreationException
     */
    public Bitmap getProfilePicture(String imageId) throws ProfilePictureCreationException
    {
        SoftReference<Bitmap> soft = this.profilePicture.get(imageId);

        // Check to see if the item was in the dictionary
        if (soft != null)
        {
            Bitmap image = soft.get();

            // Check to see if the object was not garbage collected
            if (image != null)
            {
                // We found the object in the cache, we are done!
                Log.i("KivaKatch", "Profile picture located in the cache");
                return image;
            }
            else
            {
                // Remove the entry in the Map, we will add it back in the next
                // step.
                this.lenders.remove(imageId);
            }
        }

        // Since we don't have the data in our cache, we must fetch it
        Log.i("KivaKatch", "Profile picture NOT located in the cache");

        try
        {
            // Retrieve the bitmap image
            URL aURL = new URL(KivaAPI.getSmallProfileImageURL(imageId));
            final URLConnection connection = aURL.openConnection();
            connection.connect();
            final BufferedInputStream input = new BufferedInputStream(connection.getInputStream());
            final Bitmap bitmap = BitmapFactory.decodeStream(input);
            input.close();

            // Lets cache the bitmap
            this.profilePicture.put(imageId, new SoftReference<Bitmap>(bitmap));

            // Return the image
            return bitmap;
        }
        catch (Exception e)
        {
            // Something bad happened when we tried to load the image
            Log.d("KivaKatch", "Bitmap image could not be loaded.");
            throw new ProfilePictureCreationException("Profile picture could not be loaded.", e);
        }
    }
}
