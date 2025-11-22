package POM.WebTest.BoniGarcia.Pages;


import Base.Drivers.DriverFactory;
import Base.Utils.GetDownloadDir;
import org.openqa.selenium.HasDownloads;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


public class FileDownloadPage extends AbstractPage {


    public FileDownloadPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(this.driver, this);
    }

    //Elementy na stronie
    @FindBy(xpath = "//a[@class='btn btn-outline-primary' and @download='webdrivermanager.png']")
    WebElement downloadWebdriverLogoBtn;

    @FindBy(xpath = "//a[@class='btn btn-outline-primary' and @download='webdrivermanager.pdf']")
    WebElement downloadWebdriverLogoPdfBtn;

    @FindBy(xpath = "//a[@class='btn btn-outline-primary' and @download='selenium-jupiter.png']")
    WebElement downloadSeleniumLogoBtn;

    @FindBy(xpath = "//a[@class='btn btn-outline-primary' and @download='selenium-jupiter.pdf']")
    WebElement downloadWseleniumLogoPdfBtn;

    //Metody testowe
    public void downloadFile(int choseFileToDownload) throws IOException {
        if (choseFileToDownload >= 0 && choseFileToDownload <= 4){
            List<WebElement> btns = List.of(downloadWebdriverLogoBtn, downloadWebdriverLogoPdfBtn, downloadSeleniumLogoBtn, downloadWseleniumLogoPdfBtn);
            WebElement btn = btns.get(choseFileToDownload);
            wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
        } else {
            log.error("Popraw numer przycisku! Wybierz jeden z przedziału 0-4.");
            throw new Error("Błędnie wybrany przycisk w metodzie");
        }
        WebDriver driver = DriverFactory.getDriver();
        if (driver instanceof HasDownloads downloader ) {
            System.out.println("Driver wspiera HasDownloads (RemoteWebDriver / Grid).");
            downloader.downloadFile("webdrivermanager.png", Path.of("DownloadFolder"));
        } else {
            System.out.println("Driver NIE wspiera HasDownloads (ChromeDriver lokalny).");

        }
	}

    public void downloadFile() throws IOException {
        downloadFile(0);
    }

    public boolean checkFileExistsInContainer(String containerName, String fileName) {
        try {
            // Komenda: docker exec nazwa_kontenera ls /home/seluser/Downloads/nazwa_pliku
            String[] command = {
                    "docker", "exec", containerName,
                    "ls", "/home/seluser/Downloads/" + fileName
            };

            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();

            // Jeśli exitCode == 0, to znaczy że 'ls' znalazło plik
            return exitCode == 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /***
     * Metoda weryfikuje, czy w trakcie testu plik został pobrany. W parametrze @baseNumOfFiles przekazywana jest liczba plików znajdująca się w katalogu w danej chwili.
     * Po pobraniu wykonywana jest pętla zliczająca pliki i sprawdzająca, czy liczba plików wynosi +1. Wówczas weryfikacja zakończy się pozytywnie.
     *
     */
    public File waitForDownloadedFile(File downloadFolder, int timeoutSeconds, int baseNumOfFiles) throws FileNotFoundException, InterruptedException {
        int waited = 0;
        while (waited < timeoutSeconds) {
            File[] files = downloadFolder.listFiles((_, name) ->
                    !name.endsWith(".crdownload") &&
                    !name.endsWith(".tmp") &&
                    !name.endsWith(".part")
            );

            if (files != null && files.length > baseNumOfFiles) {
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
