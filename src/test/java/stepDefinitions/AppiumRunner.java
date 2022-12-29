package stepDefinitions.Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//@RunWith(Cucumber.class)
@CucumberOptions(
        //specified tags to be executed
        tags = "",
        //path to your feature file
        features = "src/test/resources/Features",
        //specify step definition package name(Note: make sure to have this package on current directory)
        glue = {"stepDefinitions"},

        //This creates auto method name in camelCase
        //snippets = cucumber.api.SnippetType.CAMELCASE,

        //This creates cucumber reports
        plugin = {
                "pretty", "summary",
                "html:target/site/cucumber-pretty.xml",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "json:target/cucumber.json",
                "junit:target/Cucumber.xml"
        },
        monochrome = true)
public class AppiumTestRunner extends AbstractTestNGCucumberTests {
}
