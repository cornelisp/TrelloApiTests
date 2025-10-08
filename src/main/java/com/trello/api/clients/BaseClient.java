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
    protected final Config config;

    protected BaseClient() {
        this.config = Config.getInstance();
        this.requestSpec = buildRequestSpecification();
    }

    private RequestSpecification buildRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(config.getBaseUrl())
                .setContentType(ContentType.JSON)
                .addQueryParam("key", config.getApiKey())
                .addQueryParam("token", config.getApiToken())
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
