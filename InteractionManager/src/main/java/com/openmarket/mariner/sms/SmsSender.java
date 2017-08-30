package com.openmarket.mariner.sms;

import com.openmarket.mariner.resource.AuthConstants;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class SmsSender {
    private final WebTarget baseUri;

    public SmsSender(WebTarget baseUri) {
        this.baseUri = baseUri;
    }

    public String send(String msg, String destination) {
        Response response =
                 baseUri.queryParam("user", AuthConstants.MO_SMS_USERNAME)
                        .queryParam("pass", AuthConstants.MO_SMS_PASSWORD)
                        .queryParam("smsto", destination)
                        .queryParam("smsfrom", "Mariner")
                        .queryParam("smsmsg", msg)
                        .request(MediaType.APPLICATION_JSON)
                        .get();

        return response.readEntity(String.class);
    }
}
