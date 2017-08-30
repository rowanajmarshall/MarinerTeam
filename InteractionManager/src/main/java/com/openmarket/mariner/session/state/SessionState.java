package com.openmarket.mariner.session.state;

import com.openmarket.mariner.session.event.DisruptionEvent;
import com.openmarket.mariner.session.event.ResponseEvent;
import com.openmarket.mariner.session.event.TapOffEvent;
import com.openmarket.mariner.session.event.TapOnEvent;

public interface SessionState {
    SessionState handleTapOn(TapOnEvent event);
    SessionState handleTapOff(TapOffEvent event);
    SessionState handleDisruption(DisruptionEvent event);
    SessionState handleResponse(ResponseEvent event);
}
