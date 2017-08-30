package com.openmarket.mariner.session;

import com.google.inject.Inject;
import com.openmarket.mariner.journeys.JourneyService;
import com.openmarket.mariner.session.event.DisruptionEvent;
import com.openmarket.mariner.session.event.ResponseEvent;
import com.openmarket.mariner.session.event.TapOffEvent;
import com.openmarket.mariner.session.event.TapOnEvent;
import com.openmarket.mariner.session.state.InitialState;
import com.openmarket.mariner.session.state.SessionState;
import com.openmarket.mariner.sms.SmsSender;
import com.openmarket.mariner.tapons.TaponPollingJob;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionManager {
    private Logger log = LoggerFactory.getLogger(TaponPollingJob.class);

    private Map<String, SessionState> sessions = new ConcurrentHashMap<>();

    @Inject
    private SmsSender smsSender;

    @Inject
    private JourneyService journeyService;

    public void handleTapOnEvent(TapOnEvent event) {
        SessionState state = sessions.get(event.getPhoneNumber());
        if(state == null) {
            state = new InitialState(smsSender, journeyService);
            sessions.put(event.getPhoneNumber(), state);
        }
        state.handleTapOn(event);
    }

    public void handleTapOffEvent(TapOffEvent event) {
        log.info("Received tapOffEvent: " + event);
        sessions.put(event.getPhoneNumber(), sessions.get(event.getPhoneNumber()).handleTapOff(event));
    }

    public void handleDisruptionEvent(DisruptionEvent event) {
        log.info("Received DisruptionEvent: " + event);
        sessions.put(event.getPhoneNumber(), sessions.get(event.getPhoneNumber()).handleDisruption(event));
    }

    public void handleResponseEvent(ResponseEvent event) {
        log.info("Received ResponseEvent: " + event);
        sessions.put(event.getPhoneNumber(), sessions.get(event.getPhoneNumber()).handleResponse(event));
    }
}
