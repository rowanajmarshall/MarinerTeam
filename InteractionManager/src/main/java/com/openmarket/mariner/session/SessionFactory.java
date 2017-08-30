package com.openmarket.mariner.session;

import com.openmarket.mariner.journeys.JourneyService;
import com.openmarket.mariner.sms.SmsSender;

public class SessionFactory {

    private SmsSender smsSender;
    private JourneyService journeyService;

    public Session create(String phoneNumber, String startStationCode) {
        return new Session(smsSender, journeyService, phoneNumber, startStationCode);
    }
}
