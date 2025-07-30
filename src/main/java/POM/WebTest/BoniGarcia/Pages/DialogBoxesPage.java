package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

public class DialogBoxesPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;
    private JavascriptExecutor js;

    public DialogBoxesPage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "my-alert")
    public WebElement launchAlertBtn;


    @FindBy(id = "my-confirm")
    public WebElement confirmAlertBtn;


    @FindBy(id = "my-prompt")
    public WebElement promptAlertBtn;


    @FindBy(id = "my-modal")
    public WebElement modalAlertBtn;

    public void launchAlertAndChoseBtn(WebElement btn){
        wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
    }

    public void launchAlert(){
        launchAlertAndChoseBtn(launchAlertBtn);
    }
    public void launchConfirm(){
        launchAlertAndChoseBtn(confirmAlertBtn);
    }
    public void launchPrompt(){
        launchAlertAndChoseBtn(promptAlertBtn);
    }
    public void launchModal(){
        launchAlertAndChoseBtn(modalAlertBtn);
    }

    public String getTextFromAlert(){
        boolean alertPresent;
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            alertPresent = true;
        } catch (TimeoutException e) {
            alertPresent = false;
        }
        if (!alertPresent){
            launchAlert();
        }
        String alertText =  driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        return  alertText;

    }

    public String getTextFromConfirm(String btnToClickInAlert){
        boolean alertPresent;
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            alertPresent = true;
        } catch (TimeoutException e) {
            alertPresent = false;
        }
        if (!alertPresent){
            launchConfirm();
        }
        String alertText =  driver.switchTo().alert().getText();
        if (btnToClickInAlert.equalsIgnoreCase("accept") || btnToClickInAlert.equalsIgnoreCase("OK")){
            driver.switchTo().alert().accept();
        } else {
            driver.switchTo().alert().dismiss();
        }
        return  alertText;
    }

    public void setTextToPromptAlert(String promptAlertInput){
        boolean alertPresent;
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            alertPresent = true;
        } catch (TimeoutException e) {
            alertPresent = false;
        }
        if (!alertPresent){
            launchPrompt();
        }
        driver.switchTo().alert().sendKeys(promptAlertInput);
    }


}
