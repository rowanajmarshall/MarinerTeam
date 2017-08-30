package com.openmarket.mariner.session.event;

public class TapOnEvent extends BaseEvent {
    private String stationId;

    public TapOnEvent(String phoneNumber, String stationId) {
        super(phoneNumber);
        this.stationId = stationId;
    }

    public String getStationId() {
        return stationId;
    }
}
