package com.openmarket.mariner.session.state;

import com.openmarket.mariner.session.event.DisruptionEvent;
import com.openmarket.mariner.session.event.ResponseEvent;
import com.openmarket.mariner.session.event.TapOffEvent;

public class InitialState implements SessionState {
    @Override
    public void handleTapOff(TapOffEvent event) {
        // this would be quite strange
    }

    @Override
    public void handleDisruption(DisruptionEvent event) {
        // Ditto
    }

    @Override
    public void handleResponse(ResponseEvent event) {
        // Ditto
    }
}
