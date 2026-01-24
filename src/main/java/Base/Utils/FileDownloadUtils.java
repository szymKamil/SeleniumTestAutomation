package Base.Utils;

import Base.Drivers.DriverFactory;
import org.openqa.selenium.HasDownloads;
import org.slf4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
}
