package com.openmarket.mariner.session.state;

import com.openmarket.mariner.journeys.Change;
import com.openmarket.mariner.journeys.Journey;
import com.openmarket.mariner.journeys.JourneyService;
import com.openmarket.mariner.session.event.DisruptionEvent;
import com.openmarket.mariner.session.event.ResponseEvent;
import com.openmarket.mariner.session.event.TapOffEvent;
import com.openmarket.mariner.session.event.TapOnEvent;
import com.openmarket.mariner.sms.SmsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WelcomedState implements SessionState {
    private Logger log = LoggerFactory.getLogger(WelcomedState.class);

    private SmsSender smsSender;
    private JourneyService journeyService;
    private String origin;

    public WelcomedState(SmsSender smsSender, JourneyService journeyService, String origin) {
        System.out.println("New Welcomed State");
        this.smsSender = smsSender;
        this.journeyService = journeyService;
        this.origin = origin;
    }

    @Override
    public SessionState handleTapOn(TapOnEvent event) {
        log.info("Welcomed State handling tap on");
        return this;
    }

    @Override
    public SessionState handleTapOff(TapOffEvent event) {
        log.info("Welcomed State handling tap off");
        return null;
    }

    @Override
    public SessionState handleDisruption(DisruptionEvent event) {
        log.info("Welcomed State handling disruption");
        return this;
    }

    @Override
    public SessionState handleResponse(ResponseEvent event) {
        System.out.println("Welcomed State handling response");
        // Process the response by searching
        Journey journey = journeyService.getJourney(origin, event.getMessage(), false);

        StringBuilder builder = new StringBuilder("Take the ");
        for(Change change : journey.getChanges()) {
            builder.append(change.getLine()).append(" to ").append(change.getStop()).append(",");
        }
        builder.append(" arriving about ").append(journey.getArrivalTime());
        if(journey.isDisrupted()) {
            builder.append(", sorry about the poor service today");
        }
        builder.append(" - Tess");

        // Send the search results
        smsSender.send(builder.toString(), event.getPhoneNumber());

        // Switch to the Travelling state
        return new TravellingState(smsSender, journeyService, journey);
    }
}
