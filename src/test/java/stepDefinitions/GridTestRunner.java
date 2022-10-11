package stepDefinitions;

import PageMethods.commonMethods;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utilities.globalVariables;
import java.net.*;


@CucumberOptions(tags = "", features = "src/test/resources/Features",
        glue = {"stepDefinitions"},
        plugin = {
                "pretty", "summary",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "json:target/cucumber.json",
                "junit:target/Cucumber.xml"
        },
        monochrome = true)
public class GridTestRunner extends AbstractTestNGCucumberTests {

    commonMethods objCommonMethods;
    public RemoteWebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setup() throws Exception {
        String gridURL = "http://127.0.0.1:4444/wd/hub";
        System.out.println(gridURL);
        objCommonMethods = new commonMethods();
        try {
            String browserName = globalVariables.BrowserName;
            if (browserName.contains("Chrome")) {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--incognito");
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                driver = new RemoteWebDriver(new URL(gridURL), capabilities);

            }
        } catch (MalformedURLException e) {
        }
        objCommonMethods.driver = driver;
        objCommonMethods.launchBrowser();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        objCommonMethods.closebrowser();
    }

}
