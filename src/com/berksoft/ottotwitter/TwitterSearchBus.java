package com.berksoft.ottotwitter;

import com.squareup.otto.Bus;

public class TwitterSearchBus {
    
    private static final Bus sBus = new Bus();

    public static Bus getInstance() {
        return sBus;
    }
    
    private TwitterSearchBus() {}
}