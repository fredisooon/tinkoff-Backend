package com.back.backend.utils;

import com.back.backend.classes.Card;
import com.back.backend.rest.dto.CardDTO;

import java.util.ArrayList;
import java.util.List;

public class CardMapper {
    static public CardDTO mapToDTO(Card card) {
        CardDTO cardDTO = new CardDTO();

        cardDTO.setId(card.getId());
        cardDTO.setColor(card.getColor());
        cardDTO.setImg(card.getImg());
        cardDTO.setCardValue(card.getCardValue());

        return cardDTO;
    }

    static public List<CardDTO> listToDTO(List<Card> cars) {
        List<CardDTO> cardsDTO = new ArrayList<>();

        for (Card card: cars) {
            cardsDTO.add(CardMapper.mapToDTO(card));
        }

        return cardsDTO;
    }
}
