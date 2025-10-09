package com.trello.automation.base;

import com.trello.api.clients.BoardClient;
import com.trello.api.services.BoardService;
import com.trello.steps.ResponseFieldsValidationSteps;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected BoardClient boardClient;
    protected BoardService boardService;
    protected ResponseFieldsValidationSteps responseFieldsValidationSteps;

    @BeforeClass
    public void setup() {
        boardClient = new BoardClient();
        boardService = new BoardService(boardClient);
        responseFieldsValidationSteps = new ResponseFieldsValidationSteps();
    }

    @AfterClass
    public void teardown() {
        // TODO: Add any cleanup if necessary
    }

    protected void performSimpleRun() {
        responseFieldsValidationSteps.validateCorrectResponse();
    }
}