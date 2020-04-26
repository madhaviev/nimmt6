package com.game.nimmt6.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Table {

    private int id;
    private List<User> users;
    private TableStatus tableStatus;
    private List<Row> rows;
}
