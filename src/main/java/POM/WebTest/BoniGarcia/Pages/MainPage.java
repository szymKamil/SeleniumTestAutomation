package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.By;


public class MainPage {

        String boniGarciaMainURL = "https://bonigarcia.dev/selenium-webdriver-java/";
       public By mainHeader = By.xpath("//h1[@class='display-4']");

        public By getBtn(String btnName) {
               return  By.xpath("//a[@class='btn btn-outline-primary mb-2' and text()=' " + btnName + "']");
        }






}
