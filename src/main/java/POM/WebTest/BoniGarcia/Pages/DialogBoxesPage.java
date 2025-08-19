package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

public class DialogBoxesPage extends AbstractPage {




    public DialogBoxesPage(WebDriver driver, WebDriverWait wait, Logger log) {
        super(driver, wait, log);
        PageFactory.initElements(this.driver, this);
    }

    //Elementy na stronie
    @FindBy(id = "my-alert")
    public WebElement launchAlertBtn;

    @FindBy(id = "my-confirm")
    public WebElement confirmAlertBtn;

    @FindBy(id = "my-prompt")
    public WebElement promptAlertBtn;

    @FindBy(id = "my-modal")
    public WebElement modalAlertBtn;

    @FindBy(id = "confirm-text")
    public WebElement confirmTextParagraph;

    @FindBy(id = "prompt-text")
    public WebElement promptTextParagraph;

    @FindBy(id = "modal-text")
    public WebElement modalTextParagraph;

    @FindBy(id = "example-modal")
    public WebElement modalAlertBody;

    @FindBy(css = "button.btn-primary")
    public WebElement modalAlertSaveBtn;

    @FindBy(css = "button.btn-secondary")
    public WebElement modalAlertCloseBtn;

    //Metody testowe
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
            wait.until(ExpectedConditions.alertIsPresent());
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
            wait.until(ExpectedConditions.alertIsPresent());
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
        try {
            wait.until(ExpectedConditions.alertIsPresent());
        } catch (TimeoutException e) {
            launchPrompt();
            wait.until(ExpectedConditions.alertIsPresent());
        }
        Alert alert = driver.switchTo().alert();
        alert.sendKeys(promptAlertInput);
        alert.accept();
    }


    public String handleModalWindow(String saveOrClose){
        String modalBodyText;
        if(modalAlertBody.isDisplayed()){
            modalBodyText = modalAlertBody.findElement(By.cssSelector("div.modal-body")).getText();
        } else {
            launchModal();
            wait.until(ExpectedConditions.visibilityOf(modalAlertBody));
            modalBodyText = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.modal-body"))).getText();
        }
        if (saveOrClose.equalsIgnoreCase("save")){
            modalAlertSaveBtn.click();
        } else if (saveOrClose.equalsIgnoreCase("close")){
            modalAlertCloseBtn.click();
        } else {
            throw  new RuntimeException("Błędna komenda do wykonania w oknie modalnym");
        }
        return modalBodyText;
    }


}
