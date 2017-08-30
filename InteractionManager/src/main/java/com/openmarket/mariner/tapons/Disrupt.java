package com.openmarket.mariner.tapons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Disrupt {
    private String phoneNumber;

    @JsonCreator
    public Disrupt(@JsonProperty("msisdn") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("msisdn")
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
