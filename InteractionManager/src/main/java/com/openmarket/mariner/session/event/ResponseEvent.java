package com.openmarket.mariner.session.event;

public class ResponseEvent extends BaseEvent {
    String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
