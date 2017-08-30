package com.openmarket.mariner.tapons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class MessagesResponse {
    private List<Message> messages;

    @JsonCreator
    public MessagesResponse(@JsonProperty("messages") List<Message> messages) {
        this.messages = messages;
    }

    @JsonProperty
    public List<Message> getMessages() {
        return messages;
    }
}
