package com.openmarket.mariner.session.state;

import com.openmarket.mariner.journeys.Journey;
import com.openmarket.mariner.journeys.JourneyService;
import com.openmarket.mariner.session.event.DisruptionEvent;
import com.openmarket.mariner.session.event.ResponseEvent;
import com.openmarket.mariner.session.event.TapOffEvent;
import com.openmarket.mariner.session.event.TapOnEvent;
import com.openmarket.mariner.sms.SmsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TravellingState implements SessionState {
    private Logger log = LoggerFactory.getLogger(TravellingState.class);

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
        log.info("Travelling State handling tap on");
        return this;
    }

    @Override
    public SessionState handleTapOff(TapOffEvent event) {
        log.info("Travelling State handling tap off");
        smsSender.send("Thanks for travelling with us have a nice day - Tess", event.getPhoneNumber());
        return null;
    }

    @Override
    public SessionState handleDisruption(DisruptionEvent event) {
        log.info("Initial State handling disruption");
        return this;
    }

    @Override
    public SessionState handleResponse(ResponseEvent event) {
        log.info("Initial State handling response");
        return this;
    }
}
