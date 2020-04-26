package com.game.nimmt6.service;

import com.game.nimmt6.model.Card;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CardService {

    private static List<Card> cards = new ArrayList<>();

    @PostConstruct
    public void init() {
        for (int i = 1; i <= 104; i++) {
            Card card = Card.builder()
                    .number(i)
                    .build();
            if (i == 55) {
                card.setCattles(7);
            } else if (i % 11 == 0) {
                card.setCattles(5);
            } else if (i % 10 == 0) {
                card.setCattles(3);
            } else if (i % 5 == 0) {
                card.setCattles(2);
            } else {
                card.setCattles(1);
            }
            cards.add(card);
        }
    }

    Card getCardWithNumber(Integer num) {
        assert num >= 1 && num <= 104;
        return cards.stream()
                .filter(it -> num == it.getNumber())
                .findFirst()
                .get();
    }

}
