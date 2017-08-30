package com.openmarket.mariner.session;

import com.openmarket.mariner.session.event.DisruptionEvent;
import com.openmarket.mariner.session.event.ResponseEvent;
import com.openmarket.mariner.session.event.TapOffEvent;
import com.openmarket.mariner.session.state.SessionState;
import com.openmarket.mariner.tapons.Tapon;

import java.util.Map;
import java.util.TreeMap;

public class SessionManager {
    Map<String, SessionState> sessions = new TreeMap<>();

    public void initiate(Tapon tapon) {
        //
    }

    public void handleTapOffEvent(TapOffEvent event) {
        sessions.get(event.getPhoneNumber()).handleTapOff(event);
    }

    public void handelDisruptionEvent(DisruptionEvent event) {
        sessions.get(event.getPhoneNumber()).handleDisruption(event);
    }

    public void handleResponseEvent(ResponseEvent event) {
        sessions.get(event.getPhoneNumber()).handleResponse(event);
    }
}
