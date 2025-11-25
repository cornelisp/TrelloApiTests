package com.trello.api.endpoints;

import lombok.Getter;

@Getter
public enum BoardEndpoints {
    BOARDS("/1/boards"),
    GET_BOARD("/1/boards/{id}"),
    UPDATE_BOARD("/1/boards/{id}"),
    CREATE_BOARD("/1/boards"),
    DELETE_BOARD("/1/boards/{id}"),

    LISTS("/1/lists"),
    CREATE_LIST("/1/lists"),
    GET_LIST("/1/lists/{id}"),

    MEMBERS("/1/members/me/boards");

    private final String path;

    BoardEndpoints(String path) {
        this.path = path;
    }

}
