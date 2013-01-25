package com.berksoft.ottotwitter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

public class TwitterSearchActivity extends TwitterSearchBaseActivity {
    private TwitterSearchFragment mTwitterSearchFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_search);
        this.mTwitterSearchFragment = ((TwitterSearchFragment) getFragmentManager()
                .findFragmentById(R.id.activity_twitter_search_fragment));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_twitter_search, menu);
        return true;
    }

    @Subscribe
    public void onTwitterSearchQuery(
            TwitterSearchQueryMessage twitterSearchQueryMessage) {
        Intent intent = new Intent(this, TwitterSearchService.class);
        intent.setAction(TwitterSearchService.ACTION_QUERY_TWITTER);
        intent.putExtra(TwitterSearchService.EXTRA_QUERY_TWITTER_QUERY,
                twitterSearchQueryMessage.getQuery());
        startService(intent);
    }

    @Subscribe
    public void onTwitterSearchResult(TwitterSearchResultMessage message) {
        mTwitterSearchFragment.setTwitterResults(message
                .getTwitterSearchResults());
    }

    @Produce
    public TwitterSearchQueryMessage produceSearchQueryMessage() {
        return new TwitterSearchQueryMessage("Android");
    }
}