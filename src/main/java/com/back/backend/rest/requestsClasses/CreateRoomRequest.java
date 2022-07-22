package com.back.backend.rest.requestsClasses;

public class CreateRoomRequest {
    private String name;

    public CreateRoomRequest(String name) {
        this.name = name;
    }

    public CreateRoomRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
