package com.game.nimmt6.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Row {

    private List<Card> cardsInRow;
}
