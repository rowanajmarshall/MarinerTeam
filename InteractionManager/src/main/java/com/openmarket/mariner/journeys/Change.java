package com.openmarket.mariner.journeys;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class Change {
    private String line;
    private String stop;

    @JsonCreator
    public Change(@JsonProperty("line") String line, @JsonProperty("stop") String stop) {
        this.line = line;
        this.stop = stop;
    }

    @JsonProperty
    public String getLine() {
        return line;
    }

    @JsonProperty
    public String getStop() {
        return stop;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("line", line)
                          .add("stop", stop)
                          .toString();
    }
}
