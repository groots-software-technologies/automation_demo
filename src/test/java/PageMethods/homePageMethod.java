package PageMethods;

import io.cucumber.datatable.DataTable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.globalVariables;

public class homePageMethod extends commonMethods {

    public homePageMethod(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public @FindBy(id = "signInSubmit")
    WebElement button_Sign_In;

    @FindBy(xpath = "//input[@id='ap_email']")
    WebElement txt_Username;

    @FindBy(id = "continue")
    WebElement btn_Continue;

    @FindBy(xpath = "//*[@id='nav-signin-tooltip']/a")
    WebElement btn_Sign_In;

    @FindBy(xpath = "//input[@id='ap_password']")
    WebElement txt_Password;

    public @FindBy(xpath = "//span[text()='Hello. Sign in']")
    WebElement btn_Sign_In_home;

    public @FindBy(xpath = "//a[contains(@id,'link-accountList')]")
    WebElement link_accountList;

    public @FindBy(xpath = "//div[@id='nav-al-container'] //a[@id='nav-item-signout']")
    WebElement link_sign_out;

    public @FindBy(xpath = "//*[@id='nav-link-accountList']/div/span[1]")
    WebElement Hello_message;

    public boolean getToHomePage() {
        return driver.getTitle().contains("Online Shopping site in India");
    }

    //enter Username & enter Password take Username & Password from globalVariables
    public void loginToApp() {
        enterUsername(globalVariables.Username);
        clickOnContinueButton();

        enterPassword(globalVariables.Password);
        clickOnSignInButton();
    }

    public void clickOnSignInButton() {
        explicitWait(button_Sign_In, "visibilityOf", 10);
        clickElement(button_Sign_In, "Sign link");
    }

    private void clickOnContinueButton() {
        explicitWait(btn_Continue, "visibilityOf", 10);
        clickElement(btn_Continue, "Continue button");
    }

    public void enterUsername(String username) {
        explicitWait(txt_Username, "visibilityOf", 10);
        enterText(txt_Username, username);
    }

    public void enterPassword(String password) {
        explicitWait(txt_Password, "visibilityOf", 10);
        enterText(txt_Password, password);
    }

    public void clickOnSignInButtonHome() {
        explicitWait(btn_Sign_In, "visibilityOf", 10);
        clickElement(btn_Sign_In, "Sign link");
    }

    public boolean confirmLoginWasSuccessful(String message) {
        explicitWait(Hello_message, "visibilityOf", 50);
        return Hello_message.getText().contains(message);
    }

    public void logoutApp() {
        fluentWaitForElement(link_accountList, "visibilityOf", 10);
        //moveToElement(link_accountList);

        scrollDownByVisibilityOfElement(link_sign_out);
        clickElement(link_sign_out, "Sign out");
    }

    public boolean isSignPageDisplayed() {
        return driver.getTitle().equals("Amazon Sign In");
    }
}
