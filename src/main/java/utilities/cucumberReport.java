package utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.masterthought.cucumber.*;

public class cucumberReport {

    public static void generateCucumberReport() {
        File reportOutputDirectory = new File("target");
        ArrayList<String> jsonFiles = new ArrayList<String>();
        jsonFiles.add("target/cucumber.json");
        String projectName = "Amazon_Test";
        Configuration configuration = new Configuration(reportOutputDirectory, projectName);

        /*
         * configuration.addClassifications("Platform",
         * System.getProperty("os.name")); configuration.addClassifications("Browser",
         * "Chrome"); configuration.addClassifications("Branch", "release/1.0");
         */
        // optionally add metadata presented on main page via properties file
        List<String> classificationFiles = new ArrayList<String>();
        classificationFiles.add("src/test/resources/config/cucumber.properties");
        configuration.addClassificationFiles(classificationFiles);
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }

}
