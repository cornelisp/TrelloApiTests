package com.trello.api.clients;

import com.trello.api.endpoints.BoardEndpoints;
import io.restassured.response.Response;

import java.util.Map;

public class BoardClient extends BaseClient {

    public BoardClient() {
        super();
    }

    public Response getBoard(String boardId) {
        return getWithPathParams(BoardEndpoints.GET_BOARD.getPath(), Map.of("id", boardId));
    }

    public Response getAllBoardsDetails() {
        return getWithQueryParams(BoardEndpoints.MEMBERS.getPath(), Map.of("fields", "name,url"));
    }

    public Response createBoard(String name) {
        return post(BoardEndpoints.CREATE_BOARD.getPath(), Map.of(), Map.of("name", name));
    }
}
