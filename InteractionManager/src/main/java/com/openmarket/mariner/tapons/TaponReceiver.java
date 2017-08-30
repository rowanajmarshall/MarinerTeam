package com.openmarket.mariner.tapons;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.util.Optional;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class TaponReceiver {

    private static final ObjectMapper mapper = new ObjectMapper();
    private WebTarget target;

    public TaponReceiver(WebTarget target) {
        this.target = target;
    }

    public Optional<Tapon> receive() throws IOException {
        MessagesResponse response = target.path("takeMessage")
                                          .request(MediaType.APPLICATION_JSON_TYPE)
                                          .header("X-Request-Id", "123")
                                          .post(Entity.json(
                                                ImmutableMap.<String, String>of("namespace", "hackathon-lta",
                                                                                "queue", "lta-tapon-queue")))
                                          .readEntity(MessagesResponse.class);

        if(response.getMessages().isEmpty()) {
            return Optional.empty();
        }

        Message message = response.getMessages().get(0);

        Tapon tapon = mapper.readerFor(Tapon.class).readValue(message.getMessage());

        String ackResponse = target.path("ackMessage")
                                   .request(MediaType.APPLICATION_JSON_TYPE)
                                   .post(Entity.json(ImmutableMap.<String, String>of("namespace", "hackathon-lta",
                                                                                        "queue", "lta-tapon-queue",
                                                                                     "receipt", message.getReceipt())))
                                   .readEntity(String.class);
        return Optional.of(tapon);
    }
}
