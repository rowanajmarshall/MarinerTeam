package com.openmarket.mariner.session.event;

public class BaseEvent {
    private String phoneNumber;

    public BaseEvent(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
