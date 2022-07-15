package com.back.backend.classes;

import javax.persistence.*;

@Entity
public class DeckCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int cardId;     // foreign key
    private int deckId;     // foreign key

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getDeckId() {
        return deckId;
    }

    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }
}
