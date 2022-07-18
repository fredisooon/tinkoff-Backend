package com.back.backend.service.card;

import com.back.backend.classes.Card;
import com.back.backend.classes.repo.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;

    private final String STATIC_CARDS_PATH = "content/img/cards/";
    private final int MAX_CARD_COUNT = 108;


    private String[] getColors() {
        Colors[] colors = Colors.values();
        String[] convertedColors = new String[colors.length];

        for (int colorIndex = 0; colorIndex < colors.length; colorIndex++) {
            convertedColors[colorIndex] = colors[colorIndex].toString();
        }

        return convertedColors;
    }

    private String[] getAllCardNumbers() {
        String[] cardNumbers = new String[10];

        for (int i = 0; i < 10; i++) {
            cardNumbers[i] = Integer.toString(i);
        }

        return cardNumbers;
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

    private String getPathForCard(String color, String cardValue) {
        String pathName = this.STATIC_CARDS_PATH + cardValue;

        if (Objects.equals(cardValue, "+4") || Objects.equals(cardValue, "color")) {
            pathName += ".png";
        } else {
            pathName += color + ".png";
        }

        return pathName;
    }

    private void createCardInDataBase(String color, String cardValue) {
        Card firstCard = new Card();
        String pathName = this.getPathForCard(color, cardValue);

        firstCard.setCardValue(cardValue);
        firstCard.setColor(color);
        firstCard.setImg(pathName);

        cardRepository.save(firstCard);
    }

    private void initializeNumberCard() {
        String[] cardNumbers = this.getAllCardNumbers();
        String[] colors = this.getColors();

        for (String color : colors) {
            for (int cardIndex = 0; cardIndex < cardNumbers.length; cardIndex++) {
                this.createCardInDataBase(color, cardNumbers[cardIndex]);

                if (cardIndex != 0) {
                    this.createCardInDataBase(color, cardNumbers[cardIndex]);
                }
            }
        }
    }

    private void initializeNotNumbersCards() {
        String[] colors = this.getColors();
        String[] cards = this.getNotNumberCards();

        for (String color : colors) {
            for (int cardIndex = 0; cardIndex < cards.length; cardIndex++) {
                this.createCardInDataBase(color, cards[cardIndex]);

                if (cardIndex < cards.length - 2) {
                    this.createCardInDataBase(color, cards[cardIndex]);
                }
            }
        }

    }

    @PostConstruct
    public void initializeCards() {
        long cardsCount = cardRepository.count();

        if (cardsCount == MAX_CARD_COUNT) {
            return;
        }

        cardRepository.deleteAll();
        this.initializeNumberCard();
        this.initializeNotNumbersCards();
    }
}
