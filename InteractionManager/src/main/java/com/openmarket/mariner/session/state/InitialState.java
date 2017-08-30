package com.openmarket.mariner.session.state;

import com.openmarket.mariner.journeys.JourneyService;
import com.openmarket.mariner.session.event.DisruptionEvent;
import com.openmarket.mariner.session.event.ResponseEvent;
import com.openmarket.mariner.session.event.TapOffEvent;
import com.openmarket.mariner.session.event.TapOnEvent;
import com.openmarket.mariner.sms.SmsSender;

public class InitialState implements SessionState {

    private SmsSender smsSender;
    private JourneyService journeyService;

    public InitialState(SmsSender smsSender, JourneyService journeyService) {
        System.out.println("new Initial State");
        this.smsSender = smsSender;
        this.journeyService = journeyService;
    }

    @Override
    public SessionState handleTapOn(TapOnEvent event) {
        System.out.println("Initial State handling tap on");
        // Send Welcome
        smsSender.send("Hi, where are you going today? - Tess", event.getPhoneNumber());

        // Initiate Welcomed state
        return new WelcomedState(smsSender, journeyService, event.getStationId());
    }

    @Override
    public SessionState handleTapOff(TapOffEvent event) {
        System.out.println("Initial State handling tap off");
        // this would be quite strange
        return null;
    }

    @Override
    public SessionState handleDisruption(DisruptionEvent event) {
        System.out.println("Initial State handling disruption");
        // Ditto
        return this;
    }

    @Override
    public SessionState handleResponse(ResponseEvent event) {
        System.out.println("Initial State handling response");
        // Ditto
        return this;
    }
}
