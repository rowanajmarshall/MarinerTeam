package com.openmarket.mariner.tapons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class Tapon {
    private String phoneNumber;
    private String stationCode;

    @JsonCreator
    public Tapon(@JsonProperty("msisdn") String phoneNumber, @JsonProperty("station") String stationCode) {
        this.phoneNumber = phoneNumber;
        this.stationCode = stationCode;
    }

    @JsonProperty("msisdn")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty("station")
    public String getStationCode() {
        return stationCode;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("phoneNumber", phoneNumber)
                          .add("stationCode", stationCode)
                          .toString();
    }
}
