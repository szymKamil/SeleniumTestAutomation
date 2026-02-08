package Base.Utils;

import Base.Drivers.DriverFactory;
import org.openqa.selenium.HasDownloads;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
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
import java.util.Objects;

public class FileDownloadUtils {

	static  Logger logger = DriverFactory.getLogger();

	/***
	 * Metoda weryfikuje środowisko, na którym wykonywany jest test. W zależności od parametru, folder zapisu plików określany jest:
	 * a) w przypadku lokalnego PC, w głównym projekcie,
	 * b) w przypadku innego systemu, np. Linux, przyjmowane jest zdalne uruchomienie kontenerowe w Dokerze, przez co konfigurowany jest domyślny katalog pobierania,.
	 * W przypadku nieznalezienia katalogu jest on tworzony.
	 */
	public static Path getDownloadDirectory() throws IOException {
		boolean localTest = Utils.testIsInLocalEnv();
		Path dir;
		if (localTest) {
			dir = Path.of("DownloadFolder").toAbsolutePath();
			try {
				if (!Files.exists(dir)) {
					Files.createDirectories(dir);
				}
			} catch (IOException e) {
				throw new IOException("Błąd przy tworzeniu folderu " + e.getMessage());
			}
		} else {
			dir = Path.of("/home/seluser/Downloads/");
		}
		return dir;
	}

	public static void downloadFile(WebDriver driver, int numOfFiles) throws IOException, InterruptedException {
		String fileName = "webdrivermanager.png";
		Path downloadFolder = getDownloadDirectory();
		Path targetFile = downloadFolder.resolve(fileName);
		logger.info("Ścieżka do przeszukania to: {}", downloadFolder);
		FluentWait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(15)).pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchFileException.class, WebDriverException.class);
		if (driver instanceof RemoteWebDriver remote ) {
			logger.info("Pobieram plik przez RemoteWebDriver");
			wait.until(d -> {
				try {
					remote.downloadFile(fileName, Path.of("DownloadFolder"));
					return Files.exists(downloadFolder.resolve(fileName));
				} catch (IOException e) {
					logger.warn("Download retry: {}", e.getMessage());
					return false;
				}
			});
			waitForDownloadedFile(downloadFolder.toFile(), 15, 0);
			Assert.assertTrue(Files.exists(targetFile), "Plik nie został pobrany: " + targetFile);
			return;
		} else if (driver instanceof HasDownloads hasDownloads && !Utils.testIsInLocalEnv()) {
			logger.info("Pobieram plik przez HasDownloads");
			wait.until(d -> {
				try {
					hasDownloads.downloadFile(fileName, downloadFolder);
					return Files.exists(Path.of("DownloadFolder").resolve(fileName));
				} catch (IOException e) {
					logger.warn("Download retry: {}", e.getMessage());
					return false;
				}
			});
			Assert.assertTrue(Files.exists(targetFile));
			return;
		}
		if (Utils.testIsInLocalEnv()) {
			logger.info("Tryb lokalny - pobieram plik");
			var downloadedFile =
					waitForDownloadedFile(downloadFolder.toFile(), 30, numOfFiles);
			Assert.assertNotNull(downloadedFile);
			return;
		}
		logger.error("Nieobsługiwany tryb pobierania plików!");
		Assert.fail("Brak strategii downloadu");
	}

	public static int getNumOfFilesInDir() throws IOException {
		File[] files = getDownloadDirectory().toFile().listFiles();
		return files == null ? 0 : files.length;
	}


	/***
	 * Metoda weryfikuje, czy w trakcie testu plik został pobrany. W parametrze @baseNumOfFiles przekazywana jest liczba plików znajdująca się w katalogu w danej chwili.
	 * Po pobraniu wykonywana jest pętla zliczająca pliki i sprawdzająca, czy liczba plików wynosi +1. Wówczas weryfikacja zakończy się pozytywnie.
	 */
	public static File waitForDownloadedFile(File downloadFolder, int timeoutSeconds, int baseNumOfFiles) throws FileNotFoundException, InterruptedException {
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


	public static void clearDownloadFolderFromFiles() throws IOException {
		boolean localTest = Utils.testIsInLocalEnv();
		if (localTest) {
			var downloadFolder = getDownloadDirectory();
			logger.info("Czyszczę folder: {}",  downloadFolder.toAbsolutePath());
			if (Files.exists(downloadFolder) && Files.isDirectory(downloadFolder)) {
				try (var files = Files.list(downloadFolder)) {
					files.forEach(f -> {
						try {
							Files.deleteIfExists(f);
						} catch (IOException e) {
							logger.info("Nie udało się usunąć pliku {}, błąd: {}", f, e.getMessage());
						}
					});
				}
			}
		} else {
			((HasDownloads) DriverFactory.getDriver()).deleteDownloadableFiles();
		}
		logger.info("Wszystkie pliki z folderu pobierania zostały usunięte.");
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

}
