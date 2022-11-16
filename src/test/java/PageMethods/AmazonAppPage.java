package PageMethods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class AmazonAppPage extends commonMethods{
    public AmazonAppPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


}
