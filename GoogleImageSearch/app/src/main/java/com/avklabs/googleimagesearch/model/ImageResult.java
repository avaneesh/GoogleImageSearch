package com.avklabs.googleimagesearch.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by avkadam on 2/24/15.
 */
public class ImageResult implements Serializable {
    public String image_url;
    public String thumb_url;
    public String title;

    // responseData -> results -> [x] -> tbUrl, title, url

    public ImageResult(JSONObject jsonObject) {
        try {
            this.image_url = jsonObject.getString("url");
            this.thumb_url = jsonObject.getString("tbUrl");
            this.title = jsonObject.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageResult> fromJSONArray(JSONArray jsonArray){
        ArrayList<ImageResult> results = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                results.add(new ImageResult(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }
}
