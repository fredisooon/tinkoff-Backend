package com.back.backend.classes;

import javax.persistence.*;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isOver;

    @OneToOne(cascade = CascadeType.ALL)
    private Deck bankDeck;

    @OneToOne(cascade = CascadeType.ALL)
    private Deck gameDeck;

    @OneToOne(cascade = CascadeType.ALL)
    private Card currentCard;

    @OneToOne(cascade = CascadeType.ALL)
    private Player currentPlayerTurn;

    public Game() {}

    public void setId(Long id) {
        this.id = id;
    }

    public Game(Long id, boolean isOver, Deck bankDeck, Deck gameDeck, Card currentCard, Player currentPlayerTurn) {
        this.id = id;
        this.isOver = isOver;
        this.bankDeck = bankDeck;
        this.gameDeck = gameDeck;
        this.currentCard = currentCard;
        this.currentPlayerTurn = currentPlayerTurn;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public Deck getBankDeck() {
        return bankDeck;
    }

    public void setBankDeck(Deck bankDeck) {
        this.bankDeck = bankDeck;
    }

    public Deck getGameDeck() {
        return gameDeck;
    }

    public void setGameDeck(Deck gameDeck) {
        this.gameDeck = gameDeck;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }

    public Player getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    public void setCurrentPlayerTurn(Player currentPlayerTurn) {
        this.currentPlayerTurn = currentPlayerTurn;
    }

    public Long getId() {
        return id;
    }
}
