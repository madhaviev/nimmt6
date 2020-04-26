package com.game.nimmt6.service;

import com.game.nimmt6.model.Card;
import com.game.nimmt6.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private Map<String, Card> userToCardMap = new HashMap<>();

    public User createUser(String name, boolean isHost) {
        return User.builder()
                .isHost(isHost)
                .score(66)
                .build();
    }

    public User removeCardFromHandCards(User user, Card card) {
        user.getCardsInHand()
                .remove(card);
        return user;
    }

    public void selectCardForUser(User user, Card card) {
        userToCardMap.put(user.getName(), card);
        //TODO if all users of table selected cards publish event.
    }
}
