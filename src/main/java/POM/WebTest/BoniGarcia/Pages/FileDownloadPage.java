package POM.WebTest.BoniGarcia.Pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


public class FileDownloadPage extends AbstractPage {





    public FileDownloadPage(WebDriver driver, WebDriverWait wait, Logger log) {
        super(driver, wait, log);
        PageFactory.initElements(this.driver, this);
    }

    //Elementy na stronie
    @FindBy(xpath = "//a[@class='btn btn-outline-primary' and @download='webdrivermanager.png']")
    WebElement btnDownloadWebDriverLogo;

    @FindBy(xpath = "//a[@class='btn btn-outline-primary' and @download='webdrivermanager.pdf']")
    WebElement btnDownloadWebDriverLogoPDF;

    @FindBy(xpath = "//a[@class='btn btn-outline-primary' and @download='selenium-jupiter.png']")
    WebElement btnDownloadSeleniumLogo;

    @FindBy(xpath = "//a[@class='btn btn-outline-primary' and @download='selenium-jupiter.pdf']")
    WebElement btnDownloadSeleniumLogoPDF;

    //Metody testowe
    public void downloadFile(int choseFileToDownload) throws FileNotFoundException {
        //Hardcodowana ścieżka do folderu z testami
        //TODO: Gdy przejdę na Jenkinsa i dockera, trzeba poprawić ścieżkę
        File downloadFolder = new File("D:\\Programowanie\\Nauka\\SeleniumTestAutomation\\SeleniumTestAutomation\\DownloadFolder");

        if (choseFileToDownload >= 0 && choseFileToDownload <= 4){
            List<WebElement> btns = List.of(btnDownloadWebDriverLogo, btnDownloadWebDriverLogoPDF, btnDownloadSeleniumLogo, btnDownloadSeleniumLogoPDF);
            WebElement btn = btns.get(choseFileToDownload);
            wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
        } else {
            log.error("Popraw numer przycisku! Wybierz jeden z przedziału 0-4.");
            throw new Error("Błędnie wybrany przycisk w metodzie");
        }
        File downloadedFile;
        try {
             downloadedFile = waitForDownloadedFile(downloadFolder, 30);
        } catch (InterruptedException | FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        }

        log.info("Znaleziono pobrany plik: {}", downloadedFile.getName());

    }

    public void downloadFile() throws FileNotFoundException {
        //Metoda standardowa, pobiera pierwszy plik
        downloadFile(0);
    }

    public File waitForDownloadedFile(File downloadFolder, int timeoutSeconds) throws FileNotFoundException, InterruptedException {
        int baseNumberOfFiles = Objects.requireNonNull(downloadFolder.listFiles()).length;
        int waited = 0;
        while (waited < timeoutSeconds) {
            File[] files = downloadFolder.listFiles((_, name) ->
                    !name.endsWith(".crdownload") &&
                    !name.endsWith(".tmp") &&
                    !name.endsWith(".part")
            );

            if (files != null && files.length > baseNumberOfFiles) {
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
        throw new FileNotFoundException("Nie znaleziono pliku w folderze w ciągu " + timeoutSeconds + " sekund.");
    }

}
