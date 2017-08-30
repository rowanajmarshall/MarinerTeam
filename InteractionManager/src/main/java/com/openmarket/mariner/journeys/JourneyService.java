package com.openmarket.mariner.journeys;

import javax.ws.rs.client.WebTarget;

public class JourneyService {
    WebTarget baseUri;

    public JourneyService(WebTarget webTarget) {
        baseUri = webTarget;
    }

    public Journey getJourney(String from, String to, boolean busOnly) {
        return baseUri.path("journey")
                      .queryParam("from", from)
                      .queryParam("to", to)
                      .queryParam("bus_only", busOnly)
                      .request()
                      .get()
                      .readEntity(Journey.class);
    }
}
