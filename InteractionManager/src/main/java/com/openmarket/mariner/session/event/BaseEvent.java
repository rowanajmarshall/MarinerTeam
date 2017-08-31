package com.openmarket.mariner.session.event;

import com.google.common.base.MoreObjects;

public class BaseEvent {
    private String phoneNumber;

    public BaseEvent(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("phoneNumber", phoneNumber)
                          .toString();
    }
}
