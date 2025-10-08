package com.trello.api.services;

import com.trello.api.clients.BoardClient;
import com.trello.models.response.Board;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class BoardService {

    private final BoardClient boardClient;

    public BoardService(BoardClient boardClient) {
        this.boardClient = boardClient;
    }

    public Response getBoard(String boardId) {
        log.info("Service: Getting board with ID: {}", boardId);
        return boardClient.getBoard(boardId);
    }

    public List<Board> getAllBoardsDetails() {
        log.info("Service: Getting all boards details");
        Response response = boardClient.getAllBoardsDetails();
        return response.as(new TypeRef<>() {
        });
    }

    public Board createBoard(String name) {
        log.info("Service: Creating board with name: {}", name);
        Response response = boardClient.createBoard(name);
        return response.as(Board.class);
    }
}