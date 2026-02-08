package POM.WebTest.BoniGarcia.Pages;

import Base.Drivers.DriverFactory;
import Base.Utils.FileDownloadUtils;
import Base.Utils.Utils;
import org.apache.maven.rtinfo.internal.DefaultRuntimeInformation;
import org.openqa.selenium.*;
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
		if (choseFileToDownload >= 0 && choseFileToDownload <= 4) {
			List<WebElement> btns = List.of(downloadWebdriverLogoBtn, downloadWebdriverLogoPdfBtn, downloadSeleniumLogoBtn, downloadWseleniumLogoPdfBtn);
			WebElement btn = btns.get(choseFileToDownload);
			wait.until(ExpectedConditions.elementToBeClickable(btn))
					.click();
		} else {
			log.error("Popraw numer przycisku! Wybierz jeden z przedziału 0-4.");
			throw new WebDriverException("Błędnie wybrany przycisk w metodzie");
		}
		FileDownloadUtils.downloadFile(driver);
	}


}
