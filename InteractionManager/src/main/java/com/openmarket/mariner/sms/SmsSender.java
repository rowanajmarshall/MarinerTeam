package com.openmarket.mariner.sms;

import com.openmarket.mariner.resource.AuthConstants;
import com.openmarket.mariner.session.state.WelcomedState;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsSender {
    private Logger log = LoggerFactory.getLogger(WelcomedState.class);

    private final WebTarget baseUri;

    public SmsSender(WebTarget baseUri) {
        this.baseUri = baseUri;
    }

    public void send(String msg, String destination) {
        Response response =
                 baseUri.queryParam("user", encode(AuthConstants.MT_SMS_USERNAME))
                        .queryParam("pass", encode(AuthConstants.MT_SMS_PASSWORD))
                        .queryParam("smsto", destination)
                        .queryParam("smsfrom", encode("+447537402491"))
                        .queryParam("smsmsg", encode(msg).replace("+", "%20"))
                        .queryParam("split", "2")
                        .request(MediaType.APPLICATION_JSON)
                        .get();

        log.info("Sending " + msg + " returned sms ID: " + response.readEntity(String.class));

    }

    private String encode(String msg) {
        try {
            return URLEncoder.encode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
