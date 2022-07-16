package com.back.backend.service;

import com.back.backend.classes.Card;
import com.back.backend.classes.repo.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;

    private String[] getColors() {
        String[] colors = new String[4];

        colors[0] = "R";
        colors[1] = "B";
        colors[2] = "G";
        colors[3] = "Y";

        return colors;
    }

    private String[] getAllCardNumbers() {
        String[] colors = new String[10];

        for (int i = 0; i < 10; i++) {
            colors[i] = Integer.toString(i);
        }

        return colors;
    }

    private String[] getNotNumberCards() {
        String[] cards = new String[5];

        cards[0] = "reverse";
        cards[1] = "skip";
        cards[2] = "+2";
        cards[3] = "+4";
        cards[4] = "color";

        return cards;
    }

    private void createCardInDataBase(String color, String cardValue, String img) {
        Card firstCard = new Card();

        firstCard.setCardValue(cardValue);
        firstCard.setColor(color);
        firstCard.setImg(img);

        cardRepository.save(firstCard);
    }

    private void initializeNumberCard() {
        String[] cardNumbers = this.getAllCardNumbers();
        String[] colors = this.getColors();

        for (String color : colors) {
            for (int cardIndex = 0; cardIndex < cardNumbers.length; cardIndex++) {
                this.createCardInDataBase(color, cardNumbers[cardIndex], "img");

                if (cardIndex != 0) {
                    this.createCardInDataBase(color, cardNumbers[cardIndex], "img");
                }
            }
        }
    }

    public void initializeNotNumbersCards() {
        String[] colors = this.getColors();
        String[] cards = this.getNotNumberCards();

        for (String color : colors) {
            for (int cardIndex = 0; cardIndex < cards.length; cardIndex++) {
                this.createCardInDataBase(color, cards[cardIndex], "img");

                if (cardIndex < cards.length - 2) {
                    this.createCardInDataBase(color, cards[cardIndex], "img");
                }
            }
        }

    }

     public void initializeCards() {
        this.initializeNumberCard();
        this.initializeNotNumbersCards();
    }
}
