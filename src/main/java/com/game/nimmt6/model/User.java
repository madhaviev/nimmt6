package com.game.nimmt6.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class User {

    private String name;
    private boolean isHost;
    private boolean joined;
    private List<Card> cardsInHand;
    private int score;
}
