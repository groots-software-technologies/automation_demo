package PageMethods;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.Optional;
import utilities.cucumberLogs;
import utilities.globalVariables;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class commonMethods {

    public static WebDriver driver;
    public static FluentWait<WebDriver> fluentWait;
    public static Wait<WebDriver> fWait;

    //capture screenshots
    public static ByteArrayInputStream passedTestScreenshot = null;
    public static List<ByteArrayInputStream> screenshots = new ArrayList<>();
    public static int scCount=0;
    public void captureScreenshot(){
        passedTestScreenshot = new ByteArrayInputStream(((TakesScreenshot)
                driver).getScreenshotAs(OutputType.BYTES));
        if(!(passedTestScreenshot == null))
            screenshots.add(scCount, passedTestScreenshot);
    }

    //highlight the element
    public void highLightWebElement(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 1px solid red;');", element);
    }

    // unhighlight the element
    public void unHighLightWebElement(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].removeAttribute('style','')", element);
    }

    // to launch browser
    public void launchBrowser() throws InterruptedException {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        navigate_to_environment_URL();
    }

    //open URL based on environment DEV or UAT environment
    public static void navigate_to_environment_URL() throws InterruptedException {
        String environment = System.getProperty("environment");
        String URL;
        if (environment.contains("UAT")) {
            URL = globalVariables.UAT_URL;
            driver.get(URL);
            System.out.println(environment + " URL is Launched : " + URL);
            cucumberLogs.info(environment + " URL is Launched : " + URL);

        } else if (environment.contains("DEV")) {
            URL = globalVariables.DEV_URL;
            driver.get(URL);
            System.out.println(environment + " URL is Launched : " + URL);
            cucumberLogs.info(environment + " URL is Launched : " + URL);
        } else {
            navigate_to_URL();
        }
    }

    //open UAT URL
    public static void navigate_to_URL() throws InterruptedException {
        String URL = globalVariables.UAT_URL;
        driver.get(URL);
        Thread.sleep(2000);
        cucumberLogs.info("URL is Launched : " + URL);
    }

    //Return the driver reference
    public WebDriver getDriver() {
        return driver;
    }

    //open specific URL passed as a parameter
    public static void navigateto_URL(String URL) throws InterruptedException {
        driver.get(URL);
        System.out.println("URL launch");
        Thread.sleep(2000);
        cucumberLogs.info("URL is Launched : " + URL);
    }

    //Clear the text
    public static void clearText(WebElement elem) {
        fluentWaitForElement(elem, "visibilityOf", 10);
        elem.clear();
    }

    //explicitly wait using 'Thread.sleep()' method.
    public static void sleepWait(long waitInSeconds) throws InterruptedException {
        Thread.sleep(waitInSeconds);
    }

    //Click on element using 'click()' or javascript executor interface
    public static void clickElement(WebElement element, String elementName) {
        try {
            moveToElement(element);
            element.click();
            System.out.println(elementName + " is clicked");
            cucumberLogs.info(elementName+" is clicked");
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", element);
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", element);
            cucumberLogs.info(elementName + " is clicked by javascript");
            System.out.println(elementName + " is clicked by javascript");
        }
    }

    //Enter text
    public static void enterText(WebElement elem, String enterText) {
        moveToElement(elem);
        elem.clear();
        elem.sendKeys(enterText);
        cucumberLogs.info(enterText + " is entered");
    }

    //return page title of current opened URL
    public static String getPageTittle() {
        return driver.getTitle();
    }

    //return text of element
    public static String getTextFromElement(WebElement element) {
        return element.getText();
    }

    //return text of element
    public static String getTextFromElement(WebElement element, String elementName) {
        String Text;
        try {
            Text = element.getText();
            cucumberLogs.info("Text of '" + elementName + "' is " + Text);
            return Text;
        } catch (Exception e) {
            cucumberLogs.error("'" + elementName + "' is not in details ");

        }
        return elementName;
    }

    //close all windows or tab
    public void closebrowser() {
        driver.quit();
        System.out.println("Inside close browser");
        cucumberLogs.info("Browser got closed");
    }

    //explicitly wait for specific element
    public static boolean explicitWait(WebElement elem, String Conditions, int seconds) {
        WebDriverWait waitForElement = null;
        waitForElement = new WebDriverWait(driver, seconds);
        switch (Conditions) {
            case "visibilityOf":
                waitForElement.until(ExpectedConditions.visibilityOf(elem));
                break;
            case "elementToBeClickable":
                waitForElement.until(ExpectedConditions.elementToBeClickable(elem));
                break;
            case "elementToBeSelected":
                waitForElement.until(ExpectedConditions.elementToBeSelected(elem));
                break;
            case "alertIsPresent":
                waitForElement.until(ExpectedConditions.alertIsPresent());
                break;
            case "refreshed":
                waitForElement.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(elem)));
                break;
        }
        return true;
    }

    //explicitly wait for Text to be present in element
    public static boolean explicitWaitForTextInElement(WebElement elem, String textToBePresent, int seconds) {
        fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds((long) seconds))
                .pollingEvery(Duration.ofSeconds(5L))
                .ignoring(NoSuchElementException.class);
        return fluentWait.until(ExpectedConditions.textToBePresentInElement(elem, textToBePresent));
    }

    // Method to Scroll Down
    public void scrollDownbrowser(WebElement elem) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", elem);
    }

    // Alert Methods to accept
    public void alertAccept() {
        Alert alert;
        alert = driver.switchTo().alert();
        alert.accept();

    }

    // Alert Methods to reject
    public void alertReject() {
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
    }

    // Alert Methods to getText from alert window
    public String alertGetText() {
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }

    // getting attribute values from element
    public String getValueFromElement(WebElement elem, String Value) throws IOException {
        String ActualText = null;
        try {
            ActualText = elem.getAttribute(Value);

        } catch (Exception ignored) {
        }
        return ActualText;
    }

    // Switching to default content
    public void SwitchToDefault() {
        driver.switchTo().defaultContent();
    }

    // Switching to frame
    public void SwitchToFrame(int index) {
        driver.switchTo().frame(index);
    }

    // Dropdown value selection
    public static boolean selectDropdownItems(WebElement elem, String selectType, @Optional String SelectTypeValue) {
        Select dropdown;
        switch (selectType) {
            case "selectByValue":
                dropdown = new Select(elem);
                dropdown.selectByValue(SelectTypeValue);
                break;
            case "selectByVisibleText":
                dropdown = new Select(elem);
                dropdown.selectByVisibleText(SelectTypeValue);
                break;

        }
        return true;
    }

    //selecting dropdown item by select by index
    public static void selectByIndex(WebElement elem, int SelectIndexValue) {
        Select dropdown;
        dropdown = new Select(elem);
        dropdown.selectByIndex(SelectIndexValue);
    }

    //selecting dropdown item by select by Visible Text
    public static void selectByVisibleText(WebElement elem, String text) {
        Select dropdown;
        dropdown = new Select(elem);
        dropdown.selectByVisibleText(text);
    }

    //mouse over
    public static void moveToElement(WebElement elem) {
        Actions action = new Actions(driver);
        action.moveToElement(elem).build().perform();
    }

    //double click on element
    public void doubleClick(WebElement element) {
        Actions action = new Actions(driver);
        action.doubleClick(element).build().perform();
    }

    // To get the element with Parameterized locator
    public By parameterizeLocator(WebElement elem, String parameters) {
        By paramLocator = null;
        String[] strSeparator = elem.toString().split(": ", 2);
        String locatorType = strSeparator[0].trim();
        String locatorValue = strSeparator[1].trim();
        // System.out.println("Locator Type: " + locatorType + " , " + locatorValue);
        int count = 1;
        for (String value : parameters.split(";")) {
            locatorValue = locatorValue.replace("ParamValue_" + Integer.toString(count), value);
            count++;
        }
        switch (locatorType) {
            case "by.id":
                paramLocator = By.id(locatorValue);
                break;
            case "by.name":
                paramLocator = By.name(locatorValue);
                break;
            case "by.cssselector":
                paramLocator = By.cssSelector(locatorValue);
                break;
            case "by.linktext":
                paramLocator = By.linkText(locatorValue);
                break;
            case "by.xpath":
                paramLocator = By.xpath(locatorValue);
                break;
            case "by.partiallinktext":
                paramLocator = By.partialLinkText(locatorValue);
                break;
            case "by.classname":
                paramLocator = By.className(locatorValue);
                break;
            case "by.tagname":
                paramLocator = By.tagName(locatorValue);
                break;
        }
        System.out.println("Parameterized Locator: " + paramLocator.toString());
        return paramLocator;
    }

    // Switch window method
    public WebDriver switchToNewWindow(String newWindowTitle) {
        String childWindow;
        Set<String> openWindows = driver.getWindowHandles();
        System.out.println("Number of windows opened - " + openWindows.size());
        Iterator<String> windows = openWindows.iterator();
        while (windows.hasNext()) {
            childWindow = windows.next();
            WebDriver newDriver = driver.switchTo().window(childWindow);
            System.out.println("Title:" + newDriver.getTitle().trim());
            if (newDriver.getTitle().trim().equals(newWindowTitle.trim())) {
                System.out.println(" new driver pointing to - " + newDriver.getTitle());
                return newDriver;
            }
        }
        System.out.println("Window title containing - " + newWindowTitle + " not found and hence returning old driver");
        return driver;
    }

    //switch to new window
    public WebDriver switchToWindow(String window) {
        String childWindow;
        Set<String> openWindows = driver.getWindowHandles();
        System.out.println("Number of windows opened - " + openWindows.size());

        for (String openWindow : openWindows) {
            childWindow = openWindow;
            WebDriver newDriver = driver.switchTo().window(childWindow);
            System.out.println("Title: " + newDriver.getTitle().trim());
            if (newDriver.getTitle().trim().contains(window.trim()) || newDriver.getTitle().trim().toLowerCase().contains(window.trim().toLowerCase())) {
                System.out.println("New driver pointing to - " + newDriver.getTitle());
                return newDriver;
            }
        }

        System.out.println("Window title containing - " + window + " not found and hence returning old driver");
        return driver;
    }

    //return count of opened windows or tabs
    public int getCountOfWindowOrTabs(WebDriver driver) {
        Set<String> openWindows = driver.getWindowHandles();
        return openWindows.size();
    }

    //fluently wait for element based on condition
    public static boolean fluentWaitForElement(WebElement element, String condition, int maxTimeOut) {
        fWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds((long) maxTimeOut))
                .pollingEvery(Duration.ofSeconds(5L))
                .ignoring(NoSuchElementException.class);
        boolean status = false;
        switch (condition) {
            case "visibilityOf":
                fWait.until(ExpectedConditions.visibilityOf(element));
                status = true;
                break;
            case "elementToBeClickable":
                //fluentWait.until(ExpectedConditions.elementToBeClickable(element));
                fWait.until(ExpectedConditions.elementToBeClickable(element));
                status = true;
                break;
            case "elementToBeSelected":
                fluentWait.until(ExpectedConditions.elementToBeSelected(element));
                status = true;
                break;
            case "alertIsPresent":
                fluentWait.until(ExpectedConditions.alertIsPresent());
                status = true;
                break;
            case "textToBePresent":
                fluentWait.until(ExpectedConditions.textToBePresentInElement(element, condition));
                status = true;
                break;
            case "refreshed":
                status = fluentWait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
                break;
        }
        return status;
    }

    //scroll down by bottom
    public void scrollDownByBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    //scroll down by visibility of element
    public void scrollDownByVisibilityOfElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    //check checkbox is selected or not
    public static boolean isCheckboxSelected(WebElement element) {
        return element.isSelected();
    }

    //check element is displayed or not
    public static boolean isDisplayed(WebElement element) {
        try {
            if (element.isDisplayed())
                return element.isDisplayed();
        } catch (NoSuchElementException ex) {
            return false;
        }
        return false;
    }

    //select item from dropdown list
    public void selectItemFromDropDownList(WebElement dropdown, List<WebElement> dropDown_list, String itemName) {
        if (fluentWaitForElement(dropdown, "visibilityOf", 30)) {
            clickElement(dropdown, "dropdown");
            //iterating dropdown list
            for (WebElement wb : dropDown_list) {
                try {
                    if (wb.getText().equals(itemName)) {
                        //Thread.sleep(1000);
                        wb.click();
                        Thread.sleep(2000);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

}