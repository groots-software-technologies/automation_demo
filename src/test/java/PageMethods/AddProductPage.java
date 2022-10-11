package PageMethods;

import io.cucumber.datatable.DataTable;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import java.util.Set;

public class AddProductPage extends commonMethods {

    public AddProductPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='ap_email']")
    WebElement txt_Username;

    @FindBy(id = "continue")
    WebElement btn_Continue;

    @FindBy(xpath = "//input[@id='signInSubmit']")
    WebElement btn_Sign_In;

    @FindBy(xpath = "//*[@id='nav-signin-tooltip']/a")
    WebElement lnk_sign_in_home;

    @FindBy(xpath = "//input[@id='ap_password']")
    WebElement txt_Password;

    @FindBy(xpath = "//input[@id='twotabsearchtextbox']")
    public WebElement txt_search;

    @FindBy(xpath = "//*[contains(@class,'main-slot') and contains(@class,'search-results')]/div //*[contains(@class,'result-item')] //h2/a")
    public List<WebElement> list_search_results_items;

    @FindBy(xpath = "//select[@id='quantity']")
    public WebElement drp_quantity;

    @FindBy(xpath = "//input[@id='add-to-cart-button']")
    public WebElement btn_add_to_cart;

    @FindBy(xpath = "//a[@id='nav-cart']")
    public WebElement link_view_cart;

    @FindBy(xpath = "//span[@id='productTitle']")
    public WebElement product_title;

    @FindBy(xpath = "//form[@id='activeCartViewForm'] //div[contains(@id,'sc-item')] //span[contains(@class,'list-item')]/a //span[contains(@class,'truncate-cut')]")
    public List<WebElement> list_cart_items;

    @FindBy(xpath = "//form[@id='activeCartViewForm'] //div[contains(@id,'sc-item')] //*[contains(@class,'button-dropdown quantity')] //span //span //span[2]")
    public List<WebElement> getDrp_quantity_cart;

    @FindBy(xpath = "//form[@id='activeCartViewForm'] //div[contains(@id,'sc-item')] //*[contains(@class,'action-links')] //*[contains(@class,'action-delete')]")
    public List<WebElement> link_delete_product_cart;


    //login To Application. get username and password from the data table
    public void loginToApp(DataTable details) throws InterruptedException {
        int totalRows = details.height();

        //Write the code to handle Data Table
        for (int i = 0; i < totalRows; i++) {
            int days;
            switch (details.cell(i, 0).toLowerCase()) {
                case "username":
                    enterUsername(details.cell(i, 1));
                    clickOnContinueButton();
                    break;
                case "password":
                    enterPassword(details.cell(i, 1));
                    clickOnSignInButton();
                    break;
                default:
                    break;
            }
        }
    }

    //click on sign in -- home page
    public void clickOnSignInButtonHome() {
        explicitWait(lnk_sign_in_home, "visibilityOf", 10);
        clickElement(lnk_sign_in_home, "Sign link");
    }

    //click on continue -- sign in page
    private void clickOnContinueButton() {
        explicitWait(btn_Continue, "visibilityOf", 10);
        clickElement(btn_Continue, "Continue button");
    }

    //enter username -- sign in page
    public void enterUsername(String username) {
        explicitWait(txt_Username, "visibilityOf", 10);
        enterText(txt_Username, username);
    }

    //enter password -- sign in page
    public void enterPassword(String password) {
        explicitWait(txt_Password, "visibilityOf", 10);
        enterText(txt_Password, password);
    }

    //click on sign in -- sign in page
    public void clickOnSignInButton() {
        explicitWait(btn_Sign_In, "visibilityOf", 10);
        clickElement(btn_Sign_In, "Sign link");
    }

    //search products - inputs from data table
    public void searchProduct(DataTable details) {
        if ("Product name".equals(details.cell(0, 0))) {
            System.out.println("searching....");
            fluentWaitForElement(txt_search, "visibilityOf", 60);
            enterText(txt_search, details.cell(0, 1));
            txt_search.sendKeys(Keys.ENTER);
        }
    }

    //search products - inputs as string parameter
    public void searchProducts(String product) {
        System.out.println("searching....");

        fluentWaitForElement(txt_search, "visibilityOf", 20);
        enterText(txt_search, product);
        txt_search.sendKeys(Keys.ENTER);
    }

    //result of searched products
    public boolean isResultsDisplayed() {
        fluentWaitForElement(list_search_results_items.get(0), "visibilityOf", 20);
        System.out.println("Matched Products: " + list_search_results_items.size());
        int i = 0;
        for (WebElement element : list_search_results_items) {
            ++i;
            System.out.println("Product " + i + ", name: " + element.getText());
        }

        return list_search_results_items.size() > 0;
    }

    //select product from results
    String product;
    public void selectProduct() {
        for (WebElement element : list_search_results_items) {
            System.out.println("Product name: " + element.getText() + " is selected");
            product = element.getText();
            clickElement(element, "Product");
            break;
        }
    }

    //add products to cart
    String parentWindowTitle, quantities;
    public void addProductToCart(String quantities) throws InterruptedException {
        this.quantities = quantities;

        // It will return the parent window name as a String
        parentWindowTitle = driver.getTitle();
        System.out.println("Parent Window Title: "+parentWindowTitle);

        driver = switchToWindow(product); //switch to product page

        fluentWaitForElement(product_title, "visibilityOf", 10);
        product = product_title.getText();
        System.out.println("Product Title: " + product);

        fluentWaitForElement(drp_quantity, "visibilityOf", 10);
        selectByVisibleText(drp_quantity, quantities);

        fluentWaitForElement(btn_add_to_cart, "visibilityOf", 10);
        clickElement(btn_add_to_cart, "Add to Cart");

        Thread.sleep(1000);
        driver.close();

        driver = switchToWindow(parentWindowTitle); //close child or product page return back to parent window
    }

    public boolean isCartDisplayed() {
        fluentWaitForElement(link_view_cart, "visibilityOf", 10);
        clickElement(link_view_cart, "View  cart");
        return driver.getTitle().equals("Amazon.in Shopping Cart");
    }

    //check products is added in to the cart
    public boolean isProductInCart() throws InterruptedException {
        int i = 0;

        fluentWaitForElement(list_cart_items.get(0), "visibilityOf", 30);
        System.out.println("Total items in my cart: " + list_cart_items.size());

        for (int j = 0; j < list_cart_items.size(); j++) {
            ++i;
            System.out.println("Actual Product " + i + " name: " + list_cart_items.get(j).getText().trim());
            System.out.println("Actual Quantities: " + getDrp_quantity_cart.get(j).getText());
            System.out.println("Expected: " + product.trim() + " Quantities: " + quantities);
            if (list_cart_items.get(j).getText().toLowerCase().contains(product.toLowerCase()) ||
                    list_cart_items.get(j).getText().trim().toLowerCase().contains(product.trim().toLowerCase()) ||
                    list_cart_items.get(j).getText().toLowerCase().contains(product.substring(0, product.length()-10).toLowerCase())) {
                //if(getDrp_quantity_cart.get(j).getText().contains(quantities)){}
                highLightWebElement(driver, list_cart_items.get(j));
                highLightWebElement(driver, getDrp_quantity_cart.get(j));
                Thread.sleep(1000);
                captureScreenshot();
                return true;
            }
        }

        System.out.println("Product: " + product + ", not found in my cart");
        return false;
    }

    //delete products from cart
    public void deleteProductsFromCart() {
        fluentWaitForElement(link_view_cart, "visibilityOf", 10);
        clickElement(link_view_cart, "View cart");
        int i = 0;
        for (int j = 0; j < list_cart_items.size(); j++) {
            ++i;

            if (list_cart_items.get(j).getText().contains(product)) {
                highLightWebElement(driver, list_cart_items.get(j));
                highLightWebElement(driver, getDrp_quantity_cart.get(j));
                clickElement(link_delete_product_cart.get(j), "Delete product");
                break;
            }
        }

        System.out.println("Product name: " + product + ", is deleted from my cart");
    }
}
