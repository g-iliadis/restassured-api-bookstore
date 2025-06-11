# Bookstore API Testing Project

This project contains automated tests <br>
**Integration**
&
**Feature Acceptance**
for the **Bookstore REST API** using:

- Java SDK 21
- Maven 3.6+
- REST-assured
- Cucumber
- TestNG

The Bookstore API includes several REST endpoints
With our tests aim to:
- Validate endpoint behaviors
- Automate common used cases & negative cases
- Ensure consistent API quality through BDD tests

In order to be able to execute the tests you will need to have
proper maven and java home env variables set up:
<br>Under System variables:
- Add a new MAVEN_HOME variable:
  <br>Variable name: MAVEN_HOME
  <br>Variable value:
  <br>C:\Program Files\Apache\Maven\apache-maven-3.9.x
- Add Maven to Path:
  <br>Find the variable called Path and click Edit.
  <br>Click New and add:
  <br>C:\Program Files\Apache\Maven\apache-maven-3.9.x\bin

- Add JAVA home variable
<br>Find your JDK path -> C:\Program Files\Java\jdk-21
<br>Set JAVA_HOME Environment Variable
<br>Open System Properties → Environment Variables
<br>Under System variables, click New:
<br>Variable name: JAVA_HOME
<br>Variable value:
<br>C:\Program Files\Java\jdk-21
<br>Add also Path variable under System variables and click Edit
<br>Click New and add:
<br>%JAVA_HOME%\bin

- For your reports we will use the allure reports.
<br>We will need to add allure CLI on our computer
<br>download the latest version https://github.com/allure-framework/allure2/releases/tag/2.34.0
<br>Add the Path variable under System variables and click Edit
<br>Click New and add:
<br>C:\[where you saved your extracted allure folder]\bin

<br>**Run the tests**
<br>To run the tests and generate a report, follow these steps:
<br>Open a terminal or command prompt.
<br>Navigate to the root directory of the project.
<br>Run the following command:
<br>mvn clean test
<br>Wait for the build process to complete.

<br>**Run the tests on proper environment**
<br>You can choose between test env and demo env
<br>mvn clean test will execute your tests by default to test env 
<br>mvn clean test -Denv=demo will execute your tests to demo env 

<br>**Get the reports**
<br>Then on command prompt you can run the following command:
<br>mvn allure:serve
<br>A test report will be generated and will be visible to your browser
