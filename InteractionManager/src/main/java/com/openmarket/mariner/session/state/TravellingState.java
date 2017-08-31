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

import java.util.List;

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
        log.info("Travelling State handling disruption");

        // Replot with buses
        List<Change> changes = journey.getChanges();
        int changeId = (int)(Math.random() * (changes.size() - 1) );
        journey = journeyService.getJourney(changes.get(changeId).getStop(), changes.get(changes.size() - 1).getStop(), true);

        // Build a message
        StringBuilder builder = new StringBuilder("Sorry but there's a problem on your route. From ")
                .append(changes.get(changeId).getStop())
                .append(" take the ");
        for(Change change : journey.getChanges()) {
            builder.append(change.getLine()).append(" to ").append(change.getStop()).append(",");
        }
        builder.append(" arriving about ")
                .append(journey.getArrivalTime())
                .append(" - Tess");

        // Send the search results
        smsSender.send(builder.toString(), event.getPhoneNumber());

        return this;
    }

    @Override
    public SessionState handleResponse(ResponseEvent event) {
        log.info("Travelling State handling response");
        return this;
    }
}
