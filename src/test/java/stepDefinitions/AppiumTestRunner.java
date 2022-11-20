package stepDefinitions;

import PageMethods.commonMethods;
import factory.DriverFactory;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import utilities.globalVariables;

import java.io.IOException;

@CucumberOptions(tags = "", features = "src/test/resources/Features",
        glue = {"stepDefinitions"},
        plugin = {
                "pretty", "summary",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "json:target/cucumber.json",
                "junit:target/Cucumber.xml"
        },
        monochrome = true)
public class AppiumTestRunner extends AbstractTestNGCucumberTests {
    commonMethods objCommonMethods;
    public WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws InterruptedException {
        try {
            DriverFactory driverFactory = new DriverFactory();
            WebDriver driver = driverFactory.setDesiredCapabilities();
            objCommonMethods = new commonMethods();
            //objCommonMethods.launchBrowser();
        } catch (Exception e) {
            System.out.println("Exception in Runner class: " + e.getMessage());
            //DriverFactory.getDriver().quit();
            DriverFactory.closeAndroidDriver();
        }
    }

    @DataProvider
    @Override
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @AfterMethod
    public void tearDown() throws IOException {
        DriverFactory.closeAndroidDriver();
    }

}
