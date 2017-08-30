package com.openmarket.mariner.session.event;

public class ResponseEvent extends BaseEvent {
    private String message;

    public ResponseEvent(String phoneNumber, String message) {
        super(phoneNumber);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
