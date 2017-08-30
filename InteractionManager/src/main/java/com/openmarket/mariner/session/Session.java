package com.openmarket.mariner.session;

import com.openmarket.mariner.journeys.Journey;
import com.openmarket.mariner.journeys.JourneyService;
import com.openmarket.mariner.sms.SmsSender;

public class Session {
    private final SmsSender smsSender;
    private final String phoneNumber;
    private final String startStationCode;
    private final JourneyService journeyService;

    public Session(SmsSender smsSender, JourneyService journeyService, String phoneNumber, String startStationCode) {
        this.smsSender = smsSender;
        this.journeyService = journeyService;
        this.phoneNumber = phoneNumber;
        this.startStationCode = startStationCode;
    }

    public void tapon() {
        this.smsSender.send("Where are you going to?", phoneNumber);
    }

    public void mosms(String message) {
        Journey journey = this.journeyService.getJourney(startStationCode, message, true);

    }

    public void tapoff() {

    }
}
