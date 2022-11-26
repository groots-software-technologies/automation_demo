package utilities;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class cucumberLogs {

    // Initialize Log4j logs
    private static final Logger Log = Logger.getLogger(cucumberLogs.class.getName());
    // This is to print log for the beginning of the test case, as we usually run so
    // many test cases as a test suite

    public static void initLogger() {
        PropertyConfigurator.configure(globalVariables.projectDIR + "\\src\\test\\resources\\config\\log4j.properties");
    }

    public static void startTestCase(String sTestCaseName) {
        Log.info("****************************************");
        Log.info("$$ " + sTestCaseName + " $$");
        Log.info("****************************************");
    }

    //This is to print log for the ending of the test case
    public static void endTestCase(String sTestCaseName) {
        Log.info("****************************************");
        Log.info("$$ " + sTestCaseName + " $$");
        Log.info("$$ " + "-E---N---D-" + " $$");
        Log.info("****************************************");
        Log.info("                ");
    }

    // Need to create these methods, so that they can be called
    public static void info(String message) {
        Log.info(message);
    }

    public static void pass(String message) {
        Log.info(message);
    }

    public static void warn(String message) {
        Log.warn(message);
    }

    public static void error(String message) {
        Log.error(message);
    }

    public static void fail(String message) {
        Log.fatal(message);
    }

    public static void debug(String message) {
        Log.debug(message);
    }
}