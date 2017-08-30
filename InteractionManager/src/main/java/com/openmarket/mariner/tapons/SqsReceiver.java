package com.openmarket.mariner.tapons;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.util.Optional;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class SqsReceiver<T> {

    private static final ObjectMapper mapper = new ObjectMapper();
    private WebTarget target;
    private String queue;
    private Class<T> messageClass;

    public SqsReceiver(WebTarget target, String queue, Class<T> messageClass) {
        this.target = target;
        this.queue = queue;
        this.messageClass = messageClass;
    }

    public Optional<T> receive() throws IOException {
        MessagesResponse response = target.path("takeMessage")
                                          .request(MediaType.APPLICATION_JSON_TYPE)
                                          .header("X-Request-Id", "123")
                                          .post(Entity.json(
                                                ImmutableMap.<String, String>of("namespace", "hackathon-lta",
                                                                                "queue", queue)))
                                          .readEntity(MessagesResponse.class);

        if(response.getMessages().isEmpty()) {
            return Optional.empty();
        }

        Message message = response.getMessages().get(0);

        T messageBody = mapper.readerFor(messageClass).readValue(message.getMessage());

        String ackResponse = target.path("ackMessage")
                                   .request(MediaType.APPLICATION_JSON_TYPE)
                                   .post(Entity.json(ImmutableMap.<String, String>of("namespace", "hackathon-lta",
                                                                                        "queue", "lta-tapon-queue",
                                                                                     "receipt", message.getReceipt())))
                                   .readEntity(String.class);
        return Optional.of(messageBody);
    }
}
