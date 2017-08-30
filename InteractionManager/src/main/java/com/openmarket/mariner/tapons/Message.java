package com.openmarket.mariner.tapons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    private String message;
    private String receipt;

    @JsonCreator
    public Message(@JsonProperty("message") String message, @JsonProperty("receipt") String receipt) {
        this.message = message;
        this.receipt = receipt;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }

    @JsonProperty
    public String getReceipt() {
        return receipt;
    }
}
