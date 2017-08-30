package com.openmarket.mariner.session.state;

import com.openmarket.mariner.session.event.DisruptionEvent;
import com.openmarket.mariner.session.event.ResponseEvent;
import com.openmarket.mariner.session.event.TapOffEvent;
import com.openmarket.mariner.session.event.TapOnEvent;
import com.openmarket.mariner.sms.SmsSender;

public class InitialState implements SessionState {

    private SmsSender smsSender;

    public InitialState(SmsSender smsSender) {
        this.smsSender = smsSender;
    }

    @Override
    public SessionState handleTapOn(TapOnEvent event) {
        // Send Welcome
        smsSender.send("Hi, where are you going today? - Tess", event.getPhoneNumber());

        // Initiate Welcomed state
        return this;
    }

    @Override
    public SessionState handleTapOff(TapOffEvent event) {
        // this would be quite strange
        return this;
    }

    @Override
    public SessionState handleDisruption(DisruptionEvent event) {
        // Ditto
        return this;
    }

    @Override
    public SessionState handleResponse(ResponseEvent event) {
        // Ditto
        return this;
    }
}
