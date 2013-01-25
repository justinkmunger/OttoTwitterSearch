package com.berksoft.ottotwitter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

public class TwitterSearchService extends IntentService {
    
    public static String ACTION_QUERY_TWITTER = "com.berksoft.ottotwitter.TwitterSearchService.QUERY_TWITTER";
    public static String EXTRA_QUERY_TWITTER_QUERY = "query";
    private static String TWITTER_QUERY_STRING = "http://search.twitter.com/search.json?q=";
    
    private Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            List<TwitterSearchResult> twitterSearchResults = (List<TwitterSearchResult>) message.obj;
            TwitterSearchBus
                .getInstance()
                .post(new TwitterSearchResultMessage(twitterSearchResults));
        }
    };

    public TwitterSearchService() {
        super("TwitterSearchService");
    }

    protected void onHandleIntent(Intent paramIntent) {
        String action = paramIntent.getAction();
        ArrayList<TwitterSearchResult> twitterSearchResults = new ArrayList<TwitterSearchResult>();

        if ((!TextUtils.isEmpty(action)) && (action.equals(ACTION_QUERY_TWITTER))) {
            
            String twitterQueryString = paramIntent.getExtras().getString(
                    EXTRA_QUERY_TWITTER_QUERY);
            
            if (!TextUtils.isEmpty(twitterQueryString)) {
                
                DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
                
                try {
                    HttpResponse response = localDefaultHttpClient
                            .execute(new HttpGet(TWITTER_QUERY_STRING + twitterQueryString));
                                        
                    if (response.getStatusLine().getStatusCode() == 200) {
                    
                        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
                        
                        response.getEntity().writeTo(
                                localByteArrayOutputStream);
                        
                        localByteArrayOutputStream.close();
                        
                        JSONArray twitterSearchResultsArray = new JSONObject(
                                localByteArrayOutputStream.toString())
                                .getJSONArray("results");
                        
                        for (int i = 0; i < twitterSearchResultsArray.length(); i++) {
                            twitterSearchResults.add(new TwitterSearchResult(
                                    twitterSearchResultsArray.getJSONObject(i)));
                        }
                        
                        Message message = this.mHandler
                                .obtainMessage();
                        message.obj = twitterSearchResults;
                        this.mHandler.sendMessage(message);
                    }
                    response.getEntity().getContent().close();
                } catch (ClientProtocolException cpe) {
                    cpe.printStackTrace();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}