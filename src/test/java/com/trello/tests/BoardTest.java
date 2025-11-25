package com.trello.tests;

import com.trello.base.BaseTest;
import com.trello.models.request.CreateBoardRequest;
import com.trello.models.response.Board;
import com.trello.steps.BoardSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.UUID;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;

public class BoardTest extends BaseTest {

    private BoardSteps boardSteps;

    @BeforeClass
    public void prepareTestData() {
        setup();
        boardSteps = new BoardSteps(boardClient);
    }

    @Test
    @Description("Create board with valid name")
    @Severity(CRITICAL)
    public void testCreateBoard() {
        // Arrange
        String boardName = "Test Board " + UUID.randomUUID();
        CreateBoardRequest request = boardSteps.buildCreateBoardRequest(boardName);

        // Act
        Response response = boardSteps.createBoard(request);
        Board board = boardSteps.validateAndExtractBoard(response);

        // Cleanup
        registerBoardForCleanup(board.getId());

        // Assert
        boardSteps.verifyBoardDetails(board, boardName);
    }

    @Test
    @Description("Read/Get board by ID")
    @Severity(CRITICAL)
    public void testGetBoard() {
        // Arrange
        Board createdBoard = boardSteps.createDefaultBoard();
        registerBoardForCleanup(createdBoard.getId());

        // Act
        Response response = boardSteps.getBoardById(createdBoard.getId());
        Board fetchedBoard = boardSteps.validateAndExtractBoard(response);

        // Assert
        boardSteps.verifyBoardExists(fetchedBoard, createdBoard.getId());
    }

    @Test
    @Description("Update board name and description")
    @Severity(NORMAL)
    public void testUpdateBoard() {
        // Arrange
        Board createdBoard = boardSteps.createDefaultBoard();
        registerBoardForCleanup(createdBoard.getId());

        String updatedName = "Updated Board " + UUID.randomUUID();
        String updatedDesc = "Updated description for testing";
        Map<String, String> updateParams = boardSteps.buildUpdateBoardParams(updatedName, updatedDesc);

        // Act
        Response response = boardSteps.updateBoard(createdBoard.getId(), updateParams);
        Board updatedBoard = boardSteps.validateAndExtractBoard(response);

        // Assert
        boardSteps.verifyUpdatedBoardDetails(updatedBoard, updatedName, updatedDesc);
    }

    @Test
    @Description("Delete board by ID")
    @Severity(CRITICAL)
    public void testDeleteBoard() {
        // Arrange
        Board createdBoard = boardSteps.createDefaultBoard();
        registerBoardForCleanup(createdBoard.getId());

        // Act
        Response deleteResponse = boardSteps.deleteBoard(createdBoard.getId());
        boardSteps.validateStatusCode(deleteResponse, 200);

        // Assert
        Response getResponse = boardSteps.getBoardById(createdBoard.getId());
        boardSteps.validateStatusCode(getResponse, 404);
    }

    @Test
    @Description("Create board with empty name - negative test")
    @Severity(NORMAL)
    public void testCreateBoardWithEmptyName() {
        // Arrange
        CreateBoardRequest request = boardSteps.buildCreateBoardRequest("");

        // Act
        Response response = boardSteps.createBoard(request);

        // Assert
        boardSteps.validateStatusCode(response, 400);
    }

    @Test
    @Description("Get non-existent board - negative test")
    @Severity(NORMAL)
    public void testGetNonExistentBoard() {
        // Arrange
        String nonExistentId = UUID.randomUUID().toString();

        // Act
        Response response = boardSteps.getBoardById(nonExistentId);

        // Assert
        boardSteps.validateStatusCode(response, 404);
    }

    @Test
    @Description("Delete already deleted board - negative test")
    @Severity(NORMAL)
    public void testDeleteAlreadyDeletedBoard() {
        // Arrange
        Board createdBoard = boardSteps.createDefaultBoard();
        registerBoardForCleanup(createdBoard.getId());

        // Pre-delete
        boardSteps.deleteBoard(createdBoard.getId());

        // Act
        Response secondDelete = boardSteps.deleteBoard(createdBoard.getId());

        // Assert
        boardSteps.validateStatusCode(secondDelete, 404);
    }

    @AfterClass
    public void tearDown() {
        teardown();
    }
}