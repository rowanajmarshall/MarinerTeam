package com.openmarket.mariner.journeys;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.ArrayList;

public class Journey {
    private String name;
    private boolean isDisrupted;
    private String arrivalTime;
    private ArrayList<Change> changes;

    @JsonCreator
    public Journey(String name, boolean isDisrupted, String arrivalTime, ArrayList<Change> changes) {
        this.name = name;
        this.isDisrupted = isDisrupted;
        this.arrivalTime = arrivalTime;
        this.changes = changes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDisrupted() {
        return isDisrupted;
    }

    public void setDisrupted(boolean disrupted) {
        isDisrupted = disrupted;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public ArrayList<Change> getChanges() {
        return changes;
    }

    public void setChanges(ArrayList<Change> changes) {
        this.changes = changes;
    }
}
