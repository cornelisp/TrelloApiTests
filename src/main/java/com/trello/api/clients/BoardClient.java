package com.trello.api.clients;

import io.restassured.response.Response;

import java.util.Map;

import static com.trello.api.endpoints.BoardEndpoints.*;

public class BoardClient extends BaseClient {

    public BoardClient() {
        super();
    }

    public Response getBoard(String boardId) {
        return getWithPathParams(GET_BOARD.getPath(), Map.of("id", boardId));
    }

    public Response getAllBoardsDetails() {
        return getWithQueryParams(MEMBERS.getPath(), Map.of("fields", "name,url"));
    }

    public Response createBoard(String name) {
        return post(CREATE_BOARD.getPath(), Map.of(), Map.of("name", name));
    }

    public Response updateBoard(String boardId, Map<String, String> updateParams) {
        return put(UPDATE_BOARD.getPath(), Map.of("id", boardId), updateParams);
    }

    public Response deleteBoard(String boardId) {
        return delete(DELETE_BOARD.getPath(), Map.of("id", boardId));
    }
}
