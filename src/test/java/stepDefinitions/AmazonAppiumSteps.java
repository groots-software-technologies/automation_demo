package stepDefinitions;

import PageMethods.AmazonAppPage;
import PageMethods.commonMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.asserts.SoftAssert;

public class AmazonAppiumSteps extends commonMethods{
    AmazonAppPage objAmazonAppPage;
    SoftAssert softAssert;

    @Before
    public void setUp() {
        objAmazonAppPage = new AmazonAppPage(commonMethods.driver);
        softAssert = new SoftAssert();
    }

    @After
    public void tearDown() {
    }

    @Given("I open the Amazon application")
    public void iOpenTheAmazonApplication() {

    }

    @Then("Amazon Landing page is displayed")
    public void amazonLandingPageIsDisplayed() {

    }
}
