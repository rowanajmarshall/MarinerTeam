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
        System.out.println("New Travelling State");
        this.smsSender = smsSender;
        this.journeyService = journeyService;
        this.journey = journey;
    }

    @Override
    public SessionState handleTapOn(TapOnEvent event) {
        System.out.println("Travelling State handling tap on");
        return this;
    }

    @Override
    public SessionState handleTapOff(TapOffEvent event) {
        System.out.println("Travelling State handling tap off");
        smsSender.send("Thanks for travelling with us have a nice day - Tess", event.getPhoneNumber());
        return null;
    }

    @Override
    public SessionState handleDisruption(DisruptionEvent event) {
        System.out.println("Initial State handling disruption");
        return this;
    }

    @Override
    public SessionState handleResponse(ResponseEvent event) {
        System.out.println("Initial State handling response");
        return this;
    }
}
