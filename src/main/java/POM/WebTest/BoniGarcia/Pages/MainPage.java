package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class MainPage {
    public WebDriver driver;


    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

        public String boniGarciaMainURL = "https://bonigarcia.dev/selenium-webdriver-java/";
       public By mainHeader = By.xpath("//h1[@class='display-4']");

        public By getBtn(String btnName) {
               return  By.xpath("//a[@class='btn btn-outline-primary mb-2' and text()='" + btnName + "']");
        }






}
