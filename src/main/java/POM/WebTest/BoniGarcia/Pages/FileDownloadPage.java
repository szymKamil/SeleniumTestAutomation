package POM.WebTest.BoniGarcia.Pages;

import Base.Drivers.DriverFactory;
import Base.Utils.FileDownloadUtils;
import Base.Utils.Utils;
import org.apache.maven.rtinfo.internal.DefaultRuntimeInformation;
import org.openqa.selenium.HasDownloads;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.BiDi;
import org.openqa.selenium.bidi.BiDiProvider;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


public class FileDownloadPage extends AbstractPage {

	Logger log = LoggerFactory.getLogger(FileDownloadPage.class);
	WebDriver driver = DriverFactory.getDriver();
	FluentWait<WebDriver> fluentWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofSeconds(1))
			.ignoring(NoSuchFileException.class);

	//Elementy na stronie
	@FindBy(xpath = "//a[@class='btn btn-outline-primary' and @download='webdrivermanager.png']")
	WebElement downloadWebdriverLogoBtn;
	@FindBy(xpath = "//a[@class='btn btn-outline-primary' and @download='webdrivermanager.pdf']")
	WebElement downloadWebdriverLogoPdfBtn;
	@FindBy(xpath = "//a[@class='btn btn-outline-primary' and @download='selenium-jupiter.png']")
	WebElement downloadSeleniumLogoBtn;
	@FindBy(xpath = "//a[@class='btn btn-outline-primary' and @download='selenium-jupiter.pdf']")
	WebElement downloadWseleniumLogoPdfBtn;

	public FileDownloadPage() {
		super();
		PageFactory.initElements(this.driver, this);
	}

	//Metody testowe
	public void downloadFile(int choseFileToDownload) throws IOException, InterruptedException {
		int baseNumberOfFiles = 0;
		if (Utils.testIsInLocalEnv()) {
			baseNumberOfFiles = Objects.requireNonNull(FileDownloadUtils.getDownloadDirectory()
					.toFile()
					.listFiles()).length;
		}
		if (choseFileToDownload >= 0 && choseFileToDownload <= 4) {
			List<WebElement> btns = List.of(downloadWebdriverLogoBtn, downloadWebdriverLogoPdfBtn, downloadSeleniumLogoBtn, downloadWseleniumLogoPdfBtn);
			WebElement btn = btns.get(choseFileToDownload);
			wait.until(ExpectedConditions.elementToBeClickable(btn))
					.click();
		} else {
			log.error("Popraw numer przycisku! Wybierz jeden z przedziału 0-4.");
			throw new Error("Błędnie wybrany przycisk w metodzie");
		}
		Path downloadFolder = FileDownloadUtils.getDownloadDirectory();
		log.info("Ścieżka do przeszukania to: {}", downloadFolder);
		if (driver instanceof HasDownloads downloader && !Utils.testIsInLocalEnv()) {
			//TODO: Hardcodowana nazwa pliku, do udoskonalenia w przyszłości
			log.info("Test uruchomiony zdalnie. Pobieram plik za pomocą (HasDownloads)");
			downloader.downloadFile("webdrivermanager.png", Path.of("DownloadFolder"));
		} else if (driver instanceof RemoteWebDriver remoteWebDriver && !Utils.testIsInLocalEnv()) {
			try {
				remoteWebDriver.downloadFile("webdrivermanager.png", downloadFolder);
				Path file = downloadFolder.resolve("webdrivermanager.png");
				Assert.assertTrue(Files.exists(file));
			} catch (WebDriverException e) {
				waitForDownloadedFile(downloadFolder.toFile(), 15, 0);
			}
		} else if (Utils.testIsInLocalEnv()) {
			log.info("Test uruchomiony lokalnie.");
			var downloadedFile = waitForDownloadedFile(downloadFolder.toFile(), 30, baseNumberOfFiles);
			Assert.assertNotNull(downloadedFile);
		} else {
			log.error("Problem z działaniem pobierania plików w teście!!!");
		}
	}

	public void downloadFile() throws IOException, InterruptedException {
		downloadFile(0);
	}

	public boolean checkFileExistsInContainer(String containerName, String fileName) {
		try {
			// Komenda: docker exec nazwa_kontenera ls /home/seluser/Downloads/nazwa_pliku
			String[] command = {
					"docker", "exec", containerName,
					"ls", "/home/seluser/Downloads/" + fileName
			};
			Process process = Runtime.getRuntime()
					.exec(command);
			int exitCode = process.waitFor();
			return exitCode == 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	/***
	 * Metoda weryfikuje, czy w trakcie testu plik został pobrany. W parametrze @baseNumOfFiles przekazywana jest liczba plików znajdująca się w katalogu w danej chwili.
	 * Po pobraniu wykonywana jest pętla zliczająca pliki i sprawdzająca, czy liczba plików wynosi +1. Wówczas weryfikacja zakończy się pozytywnie.
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
