import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;

import static utilities.GlobalVariables.appPath;

public class StartSession {
    public static void main(String[] args) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.UDID, "b9be02ff");   //
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        //capabilities.setCapability(MobileCapabilityType.APP, appPath);
        capabilities.setCapability("appPackage", "io.appium.android.apis");
        capabilities.setCapability("appActivity", "io.appium.android.apis.ApiDemos");

        String url ="http://127.0.0.1:4723/";

        AppiumDriver driver = new AndroidDriver(new URL(url), capabilities);

    }
}
