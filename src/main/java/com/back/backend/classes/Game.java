package com.back.backend.classes;

import javax.persistence.*;

@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private Integer currentCardIndex;   // foreign key
    private Integer currentUserTurn;    // foreign key

    private Integer bankDeck;   // foreign key
    private Integer gameDeck;   // foreign key
    private boolean isOver;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurrentCardIndex() {
        return currentCardIndex;
    }

    public void setCurrentCardIndex(Integer currentCardIndex) {
        this.currentCardIndex = currentCardIndex;
    }

    public Integer getCurrentUserTurn() {
        return currentUserTurn;
    }

    public void setCurrentUserTurn(Integer currentUserTurn) {
        this.currentUserTurn = currentUserTurn;
    }

    public Integer getBankDeck() {
        return bankDeck;
    }

    public void setBankDeck(Integer bankDeck) {
        this.bankDeck = bankDeck;
    }

    public Integer getGameDeck() {
        return gameDeck;
    }

    public void setGameDeck(Integer gameDeck) {
        this.gameDeck = gameDeck;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }
}
