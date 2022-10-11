
# Amazon E2E Automation

This repository contains a collection of regression tests for Amazon application

## Installation (pre-requisites)

1. JDK 1.8+ (make sure Java class path is set in environment variables)
2. Maven (make sure .m2 class path is set in environment variables)
3. Eclipse
4. Eclipse Plugins for
   - Maven
   - Cucumber
   - TestNG
5. Git bash to run tests from command line

## Features:

- Crisp & Clear maven folder structures
- Extensive methods in CucumberRunner class
- CucumberOptions with detailed explanation of using "tags", "glue" ,”monochrome” , “pulgins”
- Screenshots on failure feature in CucumberRunner class
- TestNG and cucumber Annotations/hooks like "@After", “@Before” etc.
- A Descriptive pom.xml and testng.xml
- Scenarios for Regression suite features and step definition files
- Methods for running tests in Firefox and Chrome and Edge browsers


# How to run test cases [from command line or git bash terminal]

- First clone this repo, keeping same folder structure and wait for a clone to finish

# To run scripts on local machine browser with tags e.g.
    mvn clean test -Dcucumber.filter.tags=@RegressionTest -Dsuite=local.xml

    mvn clean test -Dcucumber.filter.tags=@SanityTest -Dsuite=local.xml

# Change tags in above command to run a specific test. e.g.
    mvn clean test -Dcucumber.filter.tags=@wip -Dsuite=local.xml
# Run specific test cases using dry run command
    mvn clean test -Dcucumber.filter.tags=@test -Dsuite=local.xml -Dcucumber.execution.dry-run=true

# Run a specific feature
    mvn clean test -Dcucumber.features=src/test/resources/Features/LoginLogout.feature -Dsuite=local.xml

# Generate HTML Reports
  To generate html report run below command just after above command

    mvn allure:serve

  Check on terminal ip generated to view Allure report
  append it with an index.html e.g. http://192.xx.xx.xx:64937/index.html

# To run scripts on lambdatest

    mvn clean test -Dcucumber.filter.tags=@RegressionTest -Dsuite=crossbrowser.xml

  The scripts should run successfully in Chrome browser and should generate application logs

# Architecture
 **src/main/java**

    Inside this folder you can put all the application resource files. Resources for the main (real) artifact should be put in this folder.
    In this folder , you will find Utilities package. In this package , we are having common generic java classes like- cucumberLogs.java and cucumberReport
    Were cucumberLogs file is to generate the application logs file which have a specified format.
    And cucumberReports file is to generate cucumber Reports after script Execution.
    
 **src/test/java**

    Inside this folder you can put all the application test resource files. Resources for the test artifact should be put in this folder.
    In this folder, you will find 2 packages as below.

    i. StepDefinitions-  Given When Then steps are added under this package, 
                         a matching class is created for every page class.
    
    ii. PageMethods – It consist of classes respective to each page. 
                      Implementing Page factory methodology.
 
  **src/test/resources**
    
    Inside this folder you can put all the application resource files. Resources for the test  should be put in this folder.
    
    In this folder , you will find folders like Features, Config.
    
    i. Features-  Here we are writing features scenarios as below-
     
    iii. Config – In this folder, we are having properties file to respective dependencies like log4j and cucumber properties

