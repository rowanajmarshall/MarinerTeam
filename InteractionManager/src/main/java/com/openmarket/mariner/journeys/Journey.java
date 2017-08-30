package com.openmarket.mariner.journeys;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import java.util.List;

public class Journey {
    private String name;
    private boolean isDisrupted;
    private String arrivalTime;
    private List<Change> changes;

    @JsonCreator
    public Journey(@JsonProperty("name") String name, @JsonProperty("isDisrupted") boolean isDisrupted, @JsonProperty("arrivalTime") String arrivalTime, @JsonProperty("changes") List<Change> changes) {
        this.name = name;
        this.isDisrupted = isDisrupted;
        this.arrivalTime = arrivalTime;
        this.changes = changes;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public boolean isDisrupted() {
        return isDisrupted;
    }

    @JsonProperty
    public String getArrivalTime() {
        return arrivalTime;
    }

    @JsonProperty
    public List<Change> getChanges() {
        return changes;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("name", name)
                          .add("isDisrupted", isDisrupted)
                          .add("arrivalTime", arrivalTime)
                          .add("changes", changes)
                          .toString();
    }
}
