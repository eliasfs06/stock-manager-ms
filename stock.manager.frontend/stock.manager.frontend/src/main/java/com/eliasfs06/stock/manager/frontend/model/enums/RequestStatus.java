package com.eliasfs06.stock.manager.frontend.model.enums;

public enum RequestStatus {
    ACCEPT("Accepted"),
    REJECT("Rejected"),
    WAITING("Waiting");

    private String description;

    RequestStatus(String description) {
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
}
