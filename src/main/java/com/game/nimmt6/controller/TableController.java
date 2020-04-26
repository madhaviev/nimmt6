package com.game.nimmt6.controller;

import com.game.nimmt6.model.Table;
import com.game.nimmt6.service.TableService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.game.nimmt6.controller.UserController.APPLICATION_HAL_JSON;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
public class TableController {

    @Resource
    private TableService tableService;

    @RequestMapping(value = "/table", method = POST, produces = APPLICATION_HAL_JSON)
    public ResponseEntity<Table> createTable(@NonNull String name, boolean isHost) {
        return new ResponseEntity<>(tableService.createTable(), OK);
    }

    @RequestMapping(value = "/openTables", method = GET, produces = APPLICATION_HAL_JSON)
    public ResponseEntity<List<Table>> getOpenTables() {
        log.info("Open tables - {}", tableService.getOpenTables());
        return new ResponseEntity<>(tableService.getOpenTables(), OK);
    }

    @RequestMapping(value = "/table/start", method = POST, produces = APPLICATION_HAL_JSON)
    public ResponseEntity<Table> startTable(Table table) {
        return new ResponseEntity<>(tableService.startGameForTable(table), OK);
    }
}
