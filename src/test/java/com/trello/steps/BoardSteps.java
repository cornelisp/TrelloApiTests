package com.trello.steps;

import com.trello.api.clients.BoardClient;
import com.trello.models.request.CreateBoardRequest;
import com.trello.models.response.Board;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class BoardSteps {

    private final BoardClient boardClient;

    public BoardSteps(BoardClient boardClient) {
        this.boardClient = boardClient;
    }

    @Step("Prepare: Build create board request with name: {boardName}")
    public CreateBoardRequest buildCreateBoardRequest(String boardName) {
        return CreateBoardRequest.builder()
                .name(boardName)
                .build();
    }

    @Step("Prepare: Build update params (Name: {name}, Desc: {description})")
    public Map<String, String> buildUpdateBoardParams(String name, String description) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("desc", description);
        return params;
    }

    @Step("Action: Create board via API")
    public Response createBoard(CreateBoardRequest request) {
        return boardClient.createBoard(request.getName());
    }

    @Step("Action: Get board by ID: {boardId}")
    public Response getBoardById(String boardId) {
        return boardClient.getBoard(boardId);
    }

    @Step("Action: Update board ID: {boardId}")
    public Response updateBoard(String boardId, Map<String, String> updateParams) {
        return boardClient.updateBoard(boardId, updateParams);
    }

    @Step("Action: Delete board ID: {boardId}")
    public Response deleteBoard(String boardId) {
        return boardClient.deleteBoard(boardId);
    }

    @Step("Precondition: Create default board for test")
    public Board createDefaultBoard() {
        String name = "Pre-condition Board " + UUID.randomUUID();
        CreateBoardRequest request = buildCreateBoardRequest(name);
        Response response = createBoard(request);
        return validateAndExtractBoard(response);
    }


    @Step("Assert: Validate response status code is {expectedStatus}")
    public void validateStatusCode(Response response, int expectedStatus) {
        assertEquals(response.getStatusCode(), expectedStatus,
                "Expected status " + expectedStatus + " but got " + response.getStatusCode());
    }

    @Step("Assert: Validate response success and extract Board")
    public Board validateAndExtractBoard(Response response) {
        validateStatusCode(response, 200);
        Board board = response.as(Board.class);
        assertNotNull(board, "Board object should not be null");
        return board;
    }

    @Step("Assert: Verify board details match expected name")
    public void verifyBoardDetails(Board board, String expectedName) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(board.getId(), "Board ID should not be null");
        softAssert.assertEquals(board.getName(), expectedName, "Board name mismatch");
        softAssert.assertNotNull(board.getUrl(), "Board URL should not be null");
        softAssert.assertFalse(board.getId().isEmpty(), "Board ID should not be empty");
        softAssert.assertAll();
    }

    @Step("Assert: Verify board exists with ID: {expectedId}")
    public void verifyBoardExists(Board board, String expectedId) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(board.getId(), "Board ID should not be null");
        softAssert.assertEquals(board.getId(), expectedId, "Board ID mismatch");
        softAssert.assertNotNull(board.getName(), "Board name should not be null");
        softAssert.assertAll();
    }

    @Step("Assert: Verify updated board details")
    public void verifyUpdatedBoardDetails(Board board, String expectedName, String expectedDesc) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(board.getName(), expectedName, "Updated board name mismatch");
        softAssert.assertEquals(board.getDesc(), expectedDesc, "Updated board description mismatch");
        softAssert.assertNotNull(board.getId(), "Board ID should not be null");
        softAssert.assertAll();
    }
}