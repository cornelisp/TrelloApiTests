package com.trello.base;

import com.trello.api.clients.BoardClient;
import com.trello.api.services.BoardService;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BaseTest {

    private final List<String> boardIdsToCleanup = new ArrayList<>();
    protected BoardClient boardClient;
    protected BoardService boardService;

    @BeforeClass
    public void setup() {
        boardClient = new BoardClient();
        boardService = new BoardService(boardClient);
    }

    protected void registerBoardForCleanup(String boardId) {
        if (boardId != null && !boardId.isEmpty()) {
            boardIdsToCleanup.add(boardId);
            log.debug("Registered board for cleanup: {}", boardId);
        }
    }

    @AfterClass(alwaysRun = true)
    public void teardown() {
        if (boardIdsToCleanup.isEmpty()) {
            log.info("No boards to clean up.");
            return;
        }

        log.info("Starting cleanup of {} board(s)...", boardIdsToCleanup.size());
        for (String boardId : boardIdsToCleanup) {
            try {
                Response response = boardClient.deleteBoard(boardId);

                if (response.getStatusCode() == 200) {
                    log.info("Deleted board: {}", boardId);
                } else if (response.getStatusCode() == 404) {
                    log.warn("Board {} was already deleted (404).", boardId);
                } else {
                    log.error("Failed to delete board {}. Status: {}",
                            boardId, response.getStatusCode());
                }
            } catch (Exception e) {
                log.error("Error during cleanup of board {}: {}", boardId, e.getMessage());
            }
        }
        boardIdsToCleanup.clear();
    }
}