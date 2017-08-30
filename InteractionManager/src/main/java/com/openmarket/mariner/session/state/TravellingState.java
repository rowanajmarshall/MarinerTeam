package com.openmarket.mariner.session.state;

import com.openmarket.mariner.journeys.Journey;
import com.openmarket.mariner.journeys.JourneyService;
import com.openmarket.mariner.session.event.DisruptionEvent;
import com.openmarket.mariner.session.event.ResponseEvent;
import com.openmarket.mariner.session.event.TapOffEvent;
import com.openmarket.mariner.session.event.TapOnEvent;
import com.openmarket.mariner.sms.SmsSender;

public class TravellingState implements SessionState {

    private SmsSender smsSender;
    private JourneyService journeyService;
    private Journey journey;

    public TravellingState(SmsSender smsSender, JourneyService journeyService, Journey journey) {
        this.smsSender = smsSender;
        this.journeyService = journeyService;
        this.journey = journey;
    }

    @Override
    public SessionState handleTapOn(TapOnEvent event) {
        return this;
    }

    @Override
    public SessionState handleTapOff(TapOffEvent event) {
        smsSender.send("Thanks for travelling with us have a nice day - Tess", event.getPhoneNumber());
        return null;
    }

    @Override
    public SessionState handleDisruption(DisruptionEvent event) {
        return this;
    }

    @Override
    public SessionState handleResponse(ResponseEvent event) {
        return this;
    }
}
