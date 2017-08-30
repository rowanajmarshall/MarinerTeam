package com.openmarket.mariner.session;

import com.openmarket.mariner.journeys.JourneyService;
import com.openmarket.mariner.session.event.DisruptionEvent;
import com.openmarket.mariner.session.event.ResponseEvent;
import com.openmarket.mariner.session.event.TapOffEvent;
import com.openmarket.mariner.session.event.TapOnEvent;
import com.openmarket.mariner.session.state.InitialState;
import com.openmarket.mariner.session.state.SessionState;
import com.openmarket.mariner.sms.SmsSender;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionManager {
    private Logger log = LoggerFactory.getLogger(SessionManager.class);

    private Map<String, SessionState> sessions = new ConcurrentHashMap<>();

    private SmsSender smsSender;
    private JourneyService journeyService;

    public SessionManager(SmsSender smsSender, JourneyService journeyService) {
        this.smsSender = smsSender;
        this.journeyService = journeyService;
    }

    public synchronized void handleTapOnEvent(TapOnEvent event) {
        log.info("Received tapOnEvent: " + event);
        SessionState state = sessions.get(event.getPhoneNumber());
        if(state == null) {
            state = new InitialState(smsSender, journeyService);
        }
        sessions.put(event.getPhoneNumber(), state.handleTapOn(event));
    }

    public synchronized void handleTapOffEvent(TapOffEvent event) {
        log.info("Received tapOffEvent: " + event);
        sessions.get(event.getPhoneNumber()).handleTapOff(event);
        sessions.remove(event.getPhoneNumber());

    }

    public synchronized void handleDisruptionEvent(DisruptionEvent event) {
        log.info("Received DisruptionEvent: " + event);
        sessions.put(event.getPhoneNumber(), sessions.get(event.getPhoneNumber()).handleDisruption(event));
    }

    public synchronized void handleResponseEvent(ResponseEvent event) {
        log.info("Received ResponseEvent: " + event);

        sessions.put(event.getPhoneNumber(), sessions.get(event.getPhoneNumber()).handleResponse(event));
    }
}
