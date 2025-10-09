package com.trello.steps;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;

@Slf4j
public class ResponseFieldsValidationSteps {

    @Step
    public void validateCorrectResponse() {
        // TODO: Implement actual validation logic
        log.debug("Validating the correct response");
        Assert.assertTrue(true);
    }
}
