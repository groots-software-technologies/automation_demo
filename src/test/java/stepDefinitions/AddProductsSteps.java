package stepDefinitions;

import PageMethods.AddProductPage;
import PageMethods.commonMethods;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

public class AddProductsSteps extends commonMethods{
    AddProductPage objAddProductPage;
    SoftAssert softAssert;

    @Before
    public void setUp() {
        objAddProductPage = new AddProductPage(commonMethods.driver);
        softAssert = new SoftAssert();
    }

    @After
    public void tearDown() {
    }

    @When("I logged in to the amazon application using valid credentials")
    public void i_login_to_application(DataTable details) throws IOException, InterruptedException {
        objAddProductPage.clickOnSignInButtonHome();
        objAddProductPage.loginToApp(details);
    }

    @And("I searched for the product below")
    public void iSearchedBelowTheProduct(DataTable details) {
        objAddProductPage.searchProduct(details);
    }

    @Then("I see the results of searched products")
    public void iSeeSearchedProductsList() {
        softAssert.assertEquals(objAddProductPage.isResultsDisplayed(), true, "no products is displayed");
        softAssert.assertAll();
    }

    @And("I decide to choose the product")
    public void iChooseTheProduct() {
        objAddProductPage.selectProduct();
    }

    @And("I selected {string} quantities and added them to my cart")
    public void iSelectQuantitiesAndAddedThemToMyCart(String quantities) throws InterruptedException {
        objAddProductPage.addProductToCart(quantities);
    }

    @Step
    @Then("I see added products in my cart")
    public void iSeeAddedProductsInMyCart() throws InterruptedException {
        softAssert.assertEquals(objAddProductPage.isCartDisplayed(), true, "Shopping Cart page not displayed");
        softAssert.assertEquals(objAddProductPage.isProductInCart(), true, "Product not found in my cart");
        softAssert.assertAll();
    }

    @And("I searched the product as {string}")
    public void iSearchedTheProductAs(String product) {
        objAddProductPage.searchProducts(product);
    }
}
