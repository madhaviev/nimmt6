package com.game.nimmt6.controller;

import com.game.nimmt6.model.Card;
import com.game.nimmt6.model.Table;
import com.game.nimmt6.model.User;
import com.game.nimmt6.service.TableService;
import com.game.nimmt6.service.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
public class UserController {

    static final String APPLICATION_HAL_JSON = "application/hal+json";

    @Resource
    private UserService userService;

    @Resource
    private TableService tableService;

    @RequestMapping(value = "/user", method = POST, produces = APPLICATION_HAL_JSON)
    public ResponseEntity<User> createUser(@NonNull String name, boolean isHost) {
        return new ResponseEntity<>(userService.createUser(name, isHost), OK);
    }

    @RequestMapping(value = "/addUserToTable", method = POST, produces = APPLICATION_HAL_JSON)
    public ResponseEntity<Void> addUserToTable(Table table, User user) {
        tableService.addUserToTable(table, user);
        return new ResponseEntity<>(OK);
    }

    @RequestMapping(value = "/selectCardForUser", method = POST, produces = APPLICATION_HAL_JSON)
    public ResponseEntity<Void> selectCardForUser(User user, Card card) {
        userService.selectCardForUser(user, card);
        return new ResponseEntity<>(OK);
    }
}
