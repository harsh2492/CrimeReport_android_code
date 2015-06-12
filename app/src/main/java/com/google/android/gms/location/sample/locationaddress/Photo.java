package com.google.android.gms.location.sample.locationaddress;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dell on 10-05-2015.
 */
public class Photo {

    private static final String JSON_FILENAME = "filename";
    public String mFilename;
    /** Create a Photo representing an existing file on disk */
    public Photo(String filename) {
        mFilename = filename;
    }
    public Photo(JSONObject json) throws JSONException {
        mFilename = json.getString(JSON_FILENAME);
    }
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_FILENAME, mFilename);
        return json;
    }
    public String getFilename() {
        return mFilename;
    }

}
