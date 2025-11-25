package Instacja;

import org.openqa.selenium.HasDownloads;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.nio.file.Path;

public class InstanceTest {

	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();

		if (driver instanceof HasDownloads downloader) {
			System.out.println("Driver wspiera HasDownloads (RemoteWebDriver / Grid).");
		} else {
			System.out.println("Driver NIE wspiera HasDownloads (ChromeDriver lokalny).");

		}
	}
}
