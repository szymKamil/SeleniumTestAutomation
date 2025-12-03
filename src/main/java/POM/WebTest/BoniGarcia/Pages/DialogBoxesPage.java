package POM.WebTest.BoniGarcia.Pages;

import Base.Drivers.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class DialogBoxesPage extends AbstractPage {

    Actions actions;
    Logger log = LoggerFactory.getLogger(DialogBoxesPage.class);


    public DialogBoxesPage() {
        super();
        actions = new Actions(DriverFactory.getDriver());
        PageFactory.initElements(driver, this);
    }

    //Elementy na stronie
    @FindBy(id = "my-alert")
    WebElement launchAlertBtn;

    @FindBy(id = "my-confirm")
    WebElement launchConfirmBtn;

    @FindBy(id = "my-prompt")
    WebElement promptAlertBtn;

    @FindBy(id = "my-modal")
    WebElement modalAlertBtn;

    @FindBy(id = "confirm-text")
    WebElement confirmTextParagraph;

    @FindBy(id = "prompt-text")
    WebElement promptTextParagraph;

    @FindBy(id = "modal-text")
    WebElement modalTextParagraph;

    @FindBy(id = "example-modal")
    WebElement modalAlertBody;

    @FindBy(css = "button.btn-primary")
    WebElement modalAlertSaveBtn;

    @FindBy(css = "button.btn-secondary")
    WebElement modalAlertCancelBtn;

    //Metody testowe
    public void launchAlertByBtn(WebElement btn){
        wait.until(ExpectedConditions.visibilityOf(btn));
        wait.until(ExpectedConditions.elementToBeClickable(btn));
        actions.click(btn).perform();
    }

    public void launchAlert(){
        launchAlertByBtn(launchAlertBtn);
        String alertString = getTextFromAlertAndAccept();
        log.info("Alert posiada tekst: {}", alertString);
    }
    public void launchConfirm(){
        launchAlertByBtn(launchConfirmBtn);
    }
    public void launchPrompt(){
        launchAlertByBtn(promptAlertBtn);

    }
    public void launchModal(){
        launchAlertByBtn(modalAlertBtn);
    }

    public String getTextFromAlertAndAccept(){
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        return alertText;

    }

    public String getTextFromConfirm(String btnToClickInAlert){
        wait.until(ExpectedConditions.alertIsPresent());
        String alertText =  driver.switchTo().alert().getText();
        if (btnToClickInAlert.equalsIgnoreCase("accept") || btnToClickInAlert.equalsIgnoreCase("OK")){
            driver.switchTo().alert().accept();
        } else {
            driver.switchTo().alert().dismiss();
        }
        return alertText;
    }

    public String getTextFromPrompt(){
        return wait.until(ExpectedConditions.visibilityOf(promptTextParagraph)).getText();
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
            modalAlertCancelBtn.click();
        } else {
            throw  new RuntimeException("Błędna komenda do wykonania w oknie modalnym");
        }
        return modalBodyText;
    }

    public String getTextFromModal(){
        return wait.until(ExpectedConditions.visibilityOf(modalTextParagraph)).getText();
    }

    public String getTextFromConfirmParagraph(){
        return wait.until(ExpectedConditions.visibilityOf(confirmTextParagraph)).getText();
    }

}
