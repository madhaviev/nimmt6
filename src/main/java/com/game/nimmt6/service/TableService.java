package com.game.nimmt6.service;

import com.game.nimmt6.model.Card;
import com.game.nimmt6.model.Row;
import com.game.nimmt6.model.Table;
import com.game.nimmt6.model.TableStatus;
import com.game.nimmt6.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
public class TableService {

    @Resource
    private CardService cardService;

    private Random random = new Random();

    private List<Table> currentTables = new ArrayList<>();

    public Table createTable() {
        Table table = Table.builder()
                .id(random.nextInt())
                .users(new ArrayList<>())
                .build();
        currentTables.add(table);
        return table;
    }

    public List<Table> getOpenTables() {
        return currentTables.stream()
                .filter(it -> it.getTableStatus() == TableStatus.OPEN)
                .collect(Collectors.toList());
    }

    public void addUserToTable(Table table, User user) {
        table.getUsers().add(user);
    }

    public Table startGameForTable(Table table) {
        Set<Integer> generatedNumbers = new HashSet<>();
        assignRandomCardsForUsersInTable(table, generatedNumbers);
        assignRandomCardsForRows(table, generatedNumbers);
        table.setTableStatus(TableStatus.IN_PROGRESS);
        return table;
    }

    public void startNextRound(Table table) {
        Set<Integer> generatedNumbers = new HashSet<>();
        assignRandomCardsForUsersInTable(table, generatedNumbers);
        assignRandomCardsForRows(table, generatedNumbers);
    }

    private void assignRandomCardsForUsersInTable(Table table, Set<Integer> generatedNumbers) {
        for (User user : table.getUsers()) {
            while (user.getCardsInHand().size() < 10) {
                int num = random.nextInt(103) + 1;
                if (!generatedNumbers.contains(num)) {
                    user.getCardsInHand().add(cardService.getCardWithNumber(num));
                    generatedNumbers.add(num);
                }
            }
        }
    }

    private void assignRandomCardsForRows(Table table, Set<Integer> generatedNumbers) {
        for (Row row : table.getRows()) {
            while (row.getCardsInRow().size() < 2) {
                int num = random.nextInt(103) + 1;
                if (!generatedNumbers.contains(num)) {
                    row.getCardsInRow().add(cardService.getCardWithNumber(num));
                    generatedNumbers.add(num);
                }
            }
        }
    }

    public Row findClosestMatchingRow(List<Row> rows, Card card) {
        int minDiff = Integer.MAX_VALUE;
        int rowNum = -1;

        for (int i = 0; i < rows.size(); i++) {
            Row row = rows.get(i);
            List<Card> cardsInRow = row.getCardsInRow();
            if (cardsInRow.size() > 0 && cardsInRow.size() < 6) {
                int diff = card.getNumber() - cardsInRow.get(cardsInRow.size() - 1).getNumber();
                minDiff = Math.min(diff, minDiff);
                if (minDiff == diff) {
                    rowNum = i;
                }
            }
        }
        if (rowNum != -1) {
            return rows.get(rowNum);
        }
        return null;
    }

    public Optional<Row> getNextEmptyRowOnTable(Table table) {
        return table.getRows().stream()
                .filter(it -> it.getCardsInRow().isEmpty())
                .findFirst();
    }

    public Row addCardToLastSlotInRow(Row row, Card card, User user) {
        assert row.getCardsInRow().size() == 5;
        for (Card rowCard : row.getCardsInRow()) {
            user.setScore(user.getScore() - rowCard.getCattles());
        }
        user.setScore(user.getScore() - card.getCattles());
        row.setCardsInRow(emptyList());
        return row;
    }

    public Row addCardToRow(Row row, Card card) {
        row.getCardsInRow().add(card);
        return row;
    }

    public List<Card> sortCards(List<Card> cards) {
        return cards.stream()
                .sorted(Comparator.comparing(Card::getNumber))
                .collect(Collectors.toList());
    }

    public Row selectRowForReplacing(Row row, Card card, User user) {
        for (Card rowCard : row.getCardsInRow()) {
            user.setScore(user.getScore() - rowCard.getCattles());
        }
        row.setCardsInRow(Arrays.asList(card));
        return row;
    }
}
