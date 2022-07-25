package com.back.backend.rest.requestsClasses;

public class CreatePlayerRequest {
    private String name;

    public CreatePlayerRequest(String name) {
        this.name = name;
    }

    public CreatePlayerRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
