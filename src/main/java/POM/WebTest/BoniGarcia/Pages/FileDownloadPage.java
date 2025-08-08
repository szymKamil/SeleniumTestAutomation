package POM.WebTest.BoniGarcia.Pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class FileDownloadPage {


    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;


    public FileDownloadPage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//a[@class='btn btn-outline-primary' and @download='webdrivermanager.png']")
    WebElement btnDownloadWebDriverLogo;

    @FindBy(xpath = "//a[@class='btn btn-outline-primary' and @download='webdrivermanager.pdf']")
    WebElement btnDownloadWebDriverLogoPDF;

    @FindBy(xpath = "//a[@class='btn btn-outline-primary' and @download='selenium-jupiter.png']")
    WebElement btnDownloadSeleniumLogo;

    @FindBy(xpath = "//a[@class='btn btn-outline-primary' and @download='selenium-jupiter.pdf']")
    WebElement btnDownloadSeleniumLogoPDF;

    public void downloadFile(int numberOfBtnToClick){
        File downloadFolder = new File("D:\\Programowanie\\Nauka\\SeleniumTestAutomation\\SeleniumTestAutomation\\DownloadFolder");

        if (numberOfBtnToClick >= 0 && numberOfBtnToClick <= 4){
            List<WebElement> btns = List.of(btnDownloadWebDriverLogo, btnDownloadWebDriverLogoPDF, btnDownloadSeleniumLogo, btnDownloadSeleniumLogoPDF);
            WebElement btn = btns.get(numberOfBtnToClick);
            wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
        } else {
            throw new Error("Błędnie wybrany przycisk w metodzie");
        }
        File downloadedFile;
        try {
             downloadedFile = waitForDownloadedFile(downloadFolder, 30);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("Znaleziono pobrany plik: {}", downloadedFile.getName());
        boolean fileDeleted =  downloadedFile.delete();
        log.info("Plik został usunięty: {}", fileDeleted);
    }

    public void downloadFile(){
        downloadFile(0);
    }

    public File waitForDownloadedFile(File downloadFolder, int timeoutSeconds) throws InterruptedException {
        int waited = 0;
        while (waited < timeoutSeconds) {
            File[] files = downloadFolder.listFiles((dir, name) ->
                    !name.endsWith(".crdownload") &&
                    !name.endsWith(".tmp") &&
                    !name.endsWith(".part")
            );

            if (files != null && files.length > 0) {
                File lastFile = Arrays.stream(files)
                        .max(Comparator.comparingLong(File::lastModified))
                        .orElse(null);

                if (lastFile != null && lastFile.exists()) {
                    return lastFile;
                }
            }
            Thread.sleep(1000);
            waited++;
        }
        throw new RuntimeException("Nie znaleziono pliku w folderze w ciągu " + timeoutSeconds + " sekund.");
    }

}
