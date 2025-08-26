package Cucumber.Rahul.LoginPage;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
		features = "src/main/java/Cucumber/Rahul/LoginPage/loginPage.feature",
		glue = {"Cucumber.Rahul.LoginPage"},
		plugin = {"pretty", "html:target/cucumber-reports.html"}

)
public class TestRunner extends AbstractTestNGCucumberTests {


}
