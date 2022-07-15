package com.back.backend.classes;

import javax.persistence.*;

@Entity
public class UserDeck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int deckId;     // foreign key
    private int userId;     // foreign key
    private int gameId;     // foreign key

    public int getDeckId() {
        return deckId;
    }

    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}