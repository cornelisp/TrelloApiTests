package com.trello.api.clients;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public abstract class BaseClient {

    protected final RequestSpecification requestSpec;

    private static final String BASE_URL = "https://api.trello.com";
    private static final String API_KEY_ENV = "TRELLO_API_KEY";
    private static final String API_TOKEN_ENV = "TRELLO_API_TOKEN";

    protected BaseClient() {
        this.requestSpec = buildRequestSpecification();
    }

    private RequestSpecification buildRequestSpecification() {
        String apiKey = System.getenv(API_KEY_ENV);
        String apiToken = System.getenv(API_TOKEN_ENV);

        if (apiKey == null || apiToken == null) {
            log.error("Missing one or more required environment variables: {}, {}",
                    API_KEY_ENV, API_TOKEN_ENV);
            throw new IllegalStateException("Missing Trello API configuration environment variables.");
        }

        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .addQueryParam("key", apiKey)
                .addQueryParam("token", apiToken)
                .build();
    }

    protected Response getWithPathParams(String endpoint, Map<String, String> pathParams) {
        log.debug("GET request to: {} with path params: {}", endpoint, pathParams);
        return RestAssured.given()
                .spec(requestSpec)
                .pathParams(pathParams)
                .get(endpoint);
    }

    protected Response getWithQueryParams(String endpoint, Map<String, String> queryParams) {
        log.debug("GET request to: {} with query params: {}", endpoint, queryParams);
        return RestAssured.given()
                .spec(requestSpec)
                .queryParams(queryParams)
                .get(endpoint);
    }

    protected Response post(String endpoint, Map<String, String> pathParams, Map<String, String> queryParams) {
        log.debug("POST request to: {} with path params: {} and query params: {}", endpoint, pathParams, queryParams);
        return RestAssured.given()
                .spec(requestSpec)
                .pathParams(pathParams)
                .queryParams(queryParams)
                .post(endpoint);
    }
}
