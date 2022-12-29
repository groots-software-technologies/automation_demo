package stepDefinitions;

import AllureWriter.AllureEnvironmentWriter;
import PageMethods.commonMethods;
import com.google.common.collect.ImmutableMap;
import factory.DriverFactory;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import utilities.cucumberLogs;
import utilities.globalVariables;
import java.io.IOException;

import static utilities.FilesOperation.copyFile;
import static utilities.FilesOperation.deleteFile;


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
            driverFactory.setDesiredCapabilities("ios");
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
        DriverFactory.closeIosDriver();
    }


    //allure environment writer
    @BeforeSuite
    void setAllureEnvironment() throws IOException {
        deleteFile(globalVariables.projectDIR + "\\ucl-logs.log");    //delete ucl-logs.log file
        cucumberLogs.initLogger();  //initialize the logger

        AllureEnvironmentWriter.allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Browser", globalVariables.BrowserName)
                        .put("Browser.Version", "105.0")
                        .put("Platform", System.getProperty("os.name"))
                        .put("URL", "https://app-uat.ucl.ac.uk/InsideUCL/")
                        .build());
    }

    @AfterSuite
    void clean() throws IOException {
        copyFile(globalVariables.projectDIR + "\\ucl-logs.log", globalVariables.projectDIR + "\\logs\\ucl-logs.log");
    }

}
