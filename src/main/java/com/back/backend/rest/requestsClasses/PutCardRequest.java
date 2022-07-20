package com.back.backend.rest.requestsClasses;

public class PutCardRequest {
    private long userId;
    private long roomId;
    private int cardId;

    private String newColor;

    public PutCardRequest(long userId, long roomId, int cardId, String newColor) {
        this.userId = userId;
        this.roomId = roomId;
        this.cardId = cardId;
        this.newColor = newColor;
    }

    public PutCardRequest() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getNewColor() {
        return newColor;
    }

    public void setNewColor(String newColor) {
        this.newColor = newColor;
    }
}
