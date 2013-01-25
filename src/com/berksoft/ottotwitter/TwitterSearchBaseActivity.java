package com.berksoft.ottotwitter;

import android.app.Activity;

public class TwitterSearchBaseActivity extends Activity {

    @Override
    protected void onResume() {
        super.onResume();
        TwitterSearchBus.getInstance().register(this);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        TwitterSearchBus.getInstance().unregister(this);
    }
    
}