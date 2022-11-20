package factory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.cucumberLogs;
import utilities.globalVariables;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static utilities.SetProperties.appConfig;

public class DriverFactory {
    public WebDriver driver;
    public static String type = "parallel";

    public static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    /**
     * This method is used to initialize the 'threadLocalDriver' driver on the basis of given
     * browser
     *
     * @return this will return threadLocalDriver.
     */

    //for local runner
    public WebDriver setDriver() {
        System.out.println("--- Inside Set Driver ---");
        System.out.println("---------------------------------------------");
        String browserName = globalVariables.BrowserName;

        if (browserName.contains("Chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--incognito");
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
            threadLocalDriver.set(new ChromeDriver(chromeOptions));
            cucumberLogs.info(browserName+" is opened");

        } else if (browserName.contains("FireFox")) {
            WebDriverManager.firefoxdriver().setup();
            threadLocalDriver.set(new FirefoxDriver());
            cucumberLogs.info(browserName+" is opened");
        } else if (browserName.contains("internet explorer")) {
            WebDriverManager.iedriver().setup();
            threadLocalDriver.set(new InternetExplorerDriver());
            cucumberLogs.info(browserName+" is opened");
        }
        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        return getDriver();

    }

    //for CrossBrowserRunner
    //@Parameters({"browser", "version", "platform", "username", "accessKey"})
    public WebDriver setDriver(String browser, String version, String platform, String username, String accesskey, String type) throws MalformedURLException {
        boolean local  = Boolean.parseBoolean(System.getProperty("LT_TUNNEL"));
        System.out.print("System.getenv(LT_TUNNEL) = ");
        System.out.println(local);
        RemoteWebDriver connection;
        DriverFactory.type = type;

        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setCapability(CapabilityType.BROWSER_NAME, browser);
        capability.setCapability(CapabilityType.VERSION, version);
        capability.setCapability(CapabilityType.PLATFORM, platform);
        capability.setCapability("build", "Parallel-Test-3");
        capability.setCapability("tunnel",local);
        capability.setCapability("network", true);
        capability.setCapability("video", true);
        capability.setCapability("console", true);
        capability.setCapability("visual", true);

        String gridURL = "https://" + username + ":" + accesskey + "@hub.lambdatest.com/wd/hub";
        System.out.println(gridURL);

        connection = new RemoteWebDriver(new URL(gridURL), capability);
        System.out.println(capability);
        System.out.println(connection.getSessionId());

        threadLocalDriver.set(connection);
        return threadLocalDriver.get();
    }

    //for gridRunner
    public WebDriver setDriver(RemoteWebDriver connection) {
        String gridURL = "http://127.0.0.1:4444/wd/hub";
        System.out.println(gridURL);

        try {
            String browserName = globalVariables.BrowserName;
            if (browserName.contains("Chrome")) {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--incognito");
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                connection = new RemoteWebDriver(new URL(gridURL), capabilities);
                cucumberLogs.info(browserName+" is opened");
            }
        } catch (MalformedURLException ignored) {
        }

        threadLocalDriver.set(connection);
        return threadLocalDriver.get();
    }

    /**
     * this is used to get the driver with threadLocalDriver
     *
     * @return
     */

    //return the current threadLocalDriver
    public static synchronized WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    //close window opened by threadLocalDriver and remove threadLocalDriver
    public static void closeBrowser()
    {
        threadLocalDriver.get().quit();
        System.out.println(" --------------------------------------------------\nDriver closed");
        //threadLocalDriver.remove();
        cucumberLogs.info(" ---------- Driver closed ---------- ");
    }

    public static AppiumDriver<MobileElement> appiumDriver;
    public static WebDriverWait wait;
    private static AppiumDriverLocalService server;

    /**
     * setting desired capabilities
     *
     * @return
     */
    public AppiumDriver<MobileElement> setDesiredCapabilities() throws MalformedURLException, InterruptedException {

        //PropertyConfigurator.configure(System.getProperty(globalVariables.reportConfigPath));

        /**
         *  @param : APP Platform
         */

        if (globalVariables.platformName.equalsIgnoreCase("Android")) {

            /**
             * Start the Appium Service with AppiumDriverLocalService
             * Check the method in details at the bottom of the Page
             */
//			startAppiumServer();

            final DesiredCapabilities capabilities = new DesiredCapabilities();

            /**
             * For launching the test without Installing the App leave below two lines and (App) capability as Commented
             **/

//			File app = new File(System.getProperty("user.dir") + "/App/Amazon_shopping.apk");
//			capabilities.setCapability("app", app.getAbsolutePath());

            cucumberLogs.info("Set The Desired value for the Test Device in App.Config file");

            //String ANDROID_DEVICE_SOCKET = appConfig.getValue("appPackage") + "_devtools_remote";

            capabilities.setCapability("platformName", appConfig.getValue("Platform"));
            capabilities.setCapability("platformVersion",appConfig.getValue("androidVersion"));

            capabilities.setCapability("deviceName",appConfig.getValue("deviceName") );
            capabilities.setCapability("udid", appConfig.getValue("udid"));
            capabilities.setCapability("appPackage", appConfig.getValue("appPackage"));
            capabilities.setCapability("appActivity", appConfig.getValue("appActivity"));

            capabilities.setCapability("automationName", appConfig.getValue("automationName"));

            //capabilities.setCapability("instrumentApp", true);
            //capabilities.setCapability("noReset", false);
            //capabilities.setCapability("androidDeviceSocket", ANDROID_DEVICE_SOCKET);
            //capabilities.setCapability("newCommandTimeout", 150);

//    		 Starting the Appium Desktop on IP :127.0.0.1  and Port : 4723
            appiumDriver =  new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

            wait = new WebDriverWait(appiumDriver, 30);
            System.out.println("Driver Initialized: "+appiumDriver);
            cucumberLogs.info("Driver Initialized: "+appiumDriver);

//            PageFactory.initElements(driver, Amazon_CartPage_OR.class);
//            PageFactory.initElements(driver, Amazon_ProductPage_OR.class);
//            PageFactory.initElements(driver, Amazon_HamburgerMenu_OR.class);
//            PageFactory.initElements(driver, Amazon_HomePage_OR.class);
//            PageFactory.initElements(driver, Amazon_LoginPage_OR.class);
//            PageFactory.initElements(driver, Amazon_SearchResultPage_OR.class);

        }
        else {
            System.out.println("Expected Platform not specified");
            cucumberLogs.warn("Expected Platform not specified");
        }

        return getAndroidDriver();
    }

    /**
     * Stop's the Appium Server and Close the Connection
     */
    public static void closeAndroidDriver() {
        appiumDriver.quit();
        cucumberLogs.info("Android driver is quited");
    }

    /**
     * Starting the Appium Server through Code using AppiumServiceBuilder
     *
     */
    public static void startAppiumServer() {


        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();

//         Use any port, in case the default 4723 is already taken (May be by another Appium server)
//         serviceBuilder.usingAnyFreePort();

        serviceBuilder.withIPAddress("127.0.0.1");
        serviceBuilder.usingPort(4723);

        // Tell serviceBuilder where node is installed. Or set this path in an environment variable named NODE_PATH
        serviceBuilder.usingDriverExecutable(new File("/usr/local/bin/node"));

        // Tell serviceBuilder where Appium is installed. Or set this path in an environment variable named APPIUM_PATH
        serviceBuilder.withAppiumJS(new File("/usr/local/bin/appium"));

        HashMap<String, String> environment = new HashMap();
        environment.put("PATH", "/usr/local/bin:" + System.getenv("PATH"));
        serviceBuilder.withEnvironment(environment);

        server = AppiumDriverLocalService.buildService(serviceBuilder);
        server.start();
        cucumberLogs.info("Appium server is started");
    }

    /**
     * Stop's the Appium Server and Close the Connection
     */
    public static void stopAppiumServer() {
        server.stop();
        cucumberLogs.info("Appium server is stopped");
    }

    /**
     *
     * @return : driver
     */
    public AppiumDriver<MobileElement> getAndroidDriver() {
        return appiumDriver;
    }

    /**
     *
     * @return : wait
     */
    public WebDriverWait getWebDriverWait() {
        cucumberLogs.info("Waiting...");
        return wait;
    }

}
