package stepDefinitions;

import PageMethods.commonMethods;
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
    public void setup() throws InterruptedException {
        System.out.println("Inside Set up method ");
        System.out.println("---------------------------------------------");
        String browserName = globalVariables.BrowserName;
        objCommonMethods = new commonMethods();
        if (browserName.contains("Chrome")) {
            //We don't need to think about the Chromedriver.exe we are using WebDriver Manager to
            // handle that instead of adding it in our project like the following:
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--incognito");
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
            driver = new ChromeDriver(chromeOptions);

        } else if (browserName.contains("FireFox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();

        } else if (browserName.contains("internet explorer")) {
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
        }
        objCommonMethods.driver = driver;
        objCommonMethods.launchBrowser();
    }

    @DataProvider
    @Override
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @AfterMethod
    public void tearDown() throws IOException {
        objCommonMethods.closebrowser();
    }

}
