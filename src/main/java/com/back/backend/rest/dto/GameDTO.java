package com.back.backend.rest.dto;

import java.util.List;

public class GameDTO {
    private Long id;
    private boolean isOver;
    private CardDTO currentCard;
    private Long currentPlayerTurnId;
    private OpponentDTO opponent;
    private List<CardDTO> userCards;

    public GameDTO(Long id, boolean isOver, CardDTO currentCard, Long currentPlayerTurnId, OpponentDTO opponent, List<CardDTO> userCards) {
        this.id = id;
        this.isOver = isOver;
        this.currentCard = currentCard;
        this.currentPlayerTurnId = currentPlayerTurnId;
        this.opponent = opponent;
        this.userCards = userCards;
    }

    public GameDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public CardDTO getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(CardDTO currentCard) {
        this.currentCard = currentCard;
    }

    public Long getCurrentPlayerTurnId() {
        return currentPlayerTurnId;
    }

    public void setCurrentPlayerTurnId(Long currentPlayerTurnId) {
        this.currentPlayerTurnId = currentPlayerTurnId;
    }

    public OpponentDTO getOpponent() {
        return opponent;
    }

    public void setOpponent(OpponentDTO opponent) {
        this.opponent = opponent;
    }

    public List<CardDTO> getUserCards() {
        return userCards;
    }

    public void setUserCards(List<CardDTO> userCards) {
        this.userCards = userCards;
    }
}