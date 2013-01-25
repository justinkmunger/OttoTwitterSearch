package com.berksoft.ottotwitter;

public class TwitterSearchQueryMessage {
    private String mQuery;
    
    TwitterSearchQueryMessage(String query) {
        mQuery = query;
    }
    
    public String getQuery() {
        return mQuery;
    }
}