package com.berksoft.ottotwitter;

import org.json.JSONException;
import org.json.JSONObject;

public class TwitterSearchResult {
    private String mFromUser;
    private String mText;

    public TwitterSearchResult(JSONObject jsonObject) {
        try {
            mFromUser = jsonObject.getString("from_user");
            mText = jsonObject.getString("text");
            return;
        } catch (JSONException localJSONException) {
            while (true)
                localJSONException.printStackTrace();
        }
    }

    public String getFromUser() {
        return mFromUser;
    }

    public String getText() {
        return mText;
    }
}