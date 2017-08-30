package com.openmarket.mariner.session;

import com.google.inject.Inject;
import com.openmarket.mariner.session.event.DisruptionEvent;
import com.openmarket.mariner.session.event.ResponseEvent;
import com.openmarket.mariner.session.event.TapOffEvent;
import com.openmarket.mariner.session.event.TapOnEvent;
import com.openmarket.mariner.session.state.InitialState;
import com.openmarket.mariner.session.state.SessionState;
import com.openmarket.mariner.sms.SmsSender;

import java.util.Map;
import java.util.TreeMap;

public class SessionManager {
    Map<String, SessionState> sessions = new TreeMap<>();

    @Inject
    SmsSender smsSender;

    public void handleTapOnEvent(TapOnEvent event) {
        SessionState state = sessions.get(event.getPhoneNumber());
        if(state == null) {
            state = new InitialState(smsSender);
            sessions.put(event.getPhoneNumber(), state);
        }
        state.handleTapOn(event);
    }

    public void handleTapOffEvent(TapOffEvent event) {
        sessions.get(event.getPhoneNumber()).handleTapOff(event);
    }

    public void handleDisruptionEvent(DisruptionEvent event) {
        sessions.get(event.getPhoneNumber()).handleDisruption(event);
    }

    public void handleResponseEvent(ResponseEvent event) {
        sessions.get(event.getPhoneNumber()).handleResponse(event);
    }
}
