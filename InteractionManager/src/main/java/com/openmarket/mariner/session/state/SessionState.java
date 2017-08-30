package com.openmarket.mariner.session.state;

import com.openmarket.mariner.session.event.DisruptionEvent;
import com.openmarket.mariner.session.event.ResponseEvent;
import com.openmarket.mariner.session.event.TapOffEvent;

public interface SessionState {
    void handleTapOff(TapOffEvent event);
    void handleDisruption(DisruptionEvent event);
    void handleResponse(ResponseEvent event);
}
