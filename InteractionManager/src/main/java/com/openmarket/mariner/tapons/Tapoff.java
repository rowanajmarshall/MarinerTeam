package com.openmarket.mariner.tapons;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tapoff {
    private String phoneNumber;

    public Tapoff(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("msisdn")
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
