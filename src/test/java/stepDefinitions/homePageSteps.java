package stepDefinitions;

import org.testng.asserts.SoftAssert;
import PageMethods.commonMethods;
import PageMethods.homePageMethod;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;

public class homePageSteps extends commonMethods {

    homePageMethod objHomePageMethod;
    SoftAssert softAssert;

    @Before
    public void setUp() {
        objHomePageMethod = new homePageMethod(commonMethods.driver);
        softAssert = new SoftAssert();
    }

    @After
    public void tearDown() {
    }

    @Given("I navigate to the Amazon application")
    public void i_navigate_to_amazon_app() throws InterruptedException {
        navigate_to_URL();
    }

    @When("Landing page is displayed")
    public void landing_page_is_displayed() {
        softAssert.assertEquals(objHomePageMethod.getToHomePage(), true, "Landing page is not displayed");
        softAssert.assertAll();
    }

    @Then("I logged in to the application")
    public void iShouldBeLoggedIn() {
        String message = "Hello,";
        softAssert.assertEquals(objHomePageMethod.confirmLoginWasSuccessful(message), true, "Failed to login");
        softAssert.assertAll();
    }

    @And("Finally I decided to log out")
    public void iLoggedOut() {
        objHomePageMethod.logoutApp();
        softAssert.assertEquals(objHomePageMethod.isSignPageDisplayed(), true, "Sign in page not displayed");
        softAssert.assertAll();
    }

    @When("I logged in to the amazon application")
    public void iLoggedInToTheAmazonApplication() {
        objHomePageMethod.clickOnSignInButtonHome();
        objHomePageMethod.loginToApp();
    }
}
