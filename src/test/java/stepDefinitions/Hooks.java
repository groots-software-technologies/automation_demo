package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.AfterStep;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import PageMethods.commonMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

public class Hooks {
    commonMethods objCommonMethods;

    @Before
    public void setup() {
        objCommonMethods = new commonMethods();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            final ByteArrayInputStream screenshot = new ByteArrayInputStream(((TakesScreenshot)
                    objCommonMethods.driver).getScreenshotAs(OutputType.BYTES));
            Allure.addAttachment(scenario.getName(), screenshot); // stick it in the report
        }
    }

    @AfterStep
    public void captureScreenshot(Scenario scenario) {
        try {
            if (!(commonMethods.passedTestScreenshot == null)) {
                Allure.addAttachment(scenario.getName(), commonMethods.passedTestScreenshot);
            }
        } catch (Exception ignored) {}
    }

}
