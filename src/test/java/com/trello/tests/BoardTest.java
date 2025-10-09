package com.trello.tests;

import com.trello.automation.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import org.testng.annotations.Test;

import static io.qameta.allure.SeverityLevel.CRITICAL;

public class BoardTest extends BaseTest {

    // TODO: Implement test methods here, write first test here

    @Test
    @Description("Simple test to check test setup")
    @Severity(CRITICAL)
    public void simpleRun() {
        performSimpleRun();
    }
}
