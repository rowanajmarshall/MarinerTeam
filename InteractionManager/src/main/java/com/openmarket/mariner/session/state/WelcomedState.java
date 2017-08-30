package com.openmarket.mariner.session.state;

import com.openmarket.mariner.session.event.DisruptionEvent;
import com.openmarket.mariner.session.event.ResponseEvent;
import com.openmarket.mariner.session.event.TapOffEvent;
import com.openmarket.mariner.session.event.TapOnEvent;

public class WelcomedState implements SessionState {
    @Override
    public SessionState handleTapOn(TapOnEvent event) {
        return null;
    }

    @Override
    public SessionState handleTapOff(TapOffEvent event) {
        return null;
    }

    @Override
    public SessionState handleDisruption(DisruptionEvent event) {
        return null;
    }

    @Override
    public SessionState handleResponse(ResponseEvent event) {
        return null;
    }
}
