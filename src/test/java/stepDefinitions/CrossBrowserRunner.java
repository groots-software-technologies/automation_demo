package stepDefinitions;

import java.net.URL;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import PageMethods.commonMethods;

@CucumberOptions(tags = "", features = "src/test/resources/Features",
        glue = {"stepDefinitions"},
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                //	"json:target/cucumber.json",
                "junit:target/Cucumber.xml"
        },
        monochrome = true)
public class CrossBrowserRunner extends AbstractTestNGCucumberTests {

    private CrossBrowserRunner testRunner;
    private commonMethods objCommonMethods;

    public static RemoteWebDriver connection;

    @BeforeClass(alwaysRun = true)
    public void setUpCucumber() {
        testRunner = new CrossBrowserRunner();
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "version", "platform"})
    public void setUpClass(String browser, String version, String platform) throws Exception {
        System.out.println("============== Inside Runner.java =================================");
        objCommonMethods = new commonMethods();
        String username = System.getenv("LT_USERNAME") == null ? "YOUR LT_USERNAME" : System.getenv("LT_USERNAME");
        String accesskey = System.getenv("LT_ACCESS_KEY") == null ? "YOUR LT_ACCESS_KEY" : System.getenv("LT_ACCESS_KEY");
        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setCapability(CapabilityType.BROWSER_NAME, browser);
        capability.setCapability(CapabilityType.VERSION, version);
        capability.setCapability(CapabilityType.PLATFORM, platform);
        capability.setCapability("build", "Cucumber TestNG");
        capability.setCapability("network", true);
        capability.setCapability("video", true);
        capability.setCapability("console", true);
        capability.setCapability("visual", true);
        String gridURL = "https://" + username + ":" + accesskey + "@hub.lambdatest.com/wd/hub";
        System.out.println(gridURL);
        connection = new RemoteWebDriver(new URL(gridURL), capability);
        System.out.println(capability);
        System.out.println(connection.getSessionId());
        commonMethods.driver = connection;
        objCommonMethods.launchBrowser();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        if (connection != null) {
            connection.quit();
        }
    }
}
