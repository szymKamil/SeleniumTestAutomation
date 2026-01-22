package Cucumber.Rahul.VegetablesShop;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
		features = "src/main/java/Cucumber/Rahul/VegetablesShop/vegetableShop.feature",
		glue = {"Cucumber.Rahul.VegetablesShop"},
		plugin = {"pretty", "html:target/cucumber-reports.html"}

)
public class TestRunner extends AbstractTestNGCucumberTests {}
