package Base.Utils;

import Base.Drivers.DriverFactory;
import org.openqa.selenium.HasDownloads;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileDownloadUtils {


	/***
	 * Metoda weryfikuje środowisko, na którym wykonywany jest test. W zależności od parametru, folder zapisu plików określany jest:
	 * a) w przypadku lokalnego PC, w głównym projekcie,
	 * b) w przypadku innego systemu, np. Linux, przyjmowane jest zdalne uruchomienie kontenerowe w Dokerze, przez co konfigurowany jest domyślny katalog pobierania,.
	 * W przypadku nieznalezienia katalogu jest on tworzony.
	 */
	public static Path getDownloadDirectory(){
		boolean localTest = Utils.testIsInLocalEnv();
		Path dir;
		if (localTest) {
			dir = Path.of("DownloadFolder").toAbsolutePath();
			try {
				if (!Files.exists(dir)) {
					Files.createDirectories(dir);
				}
			} catch (Exception e) {
				throw new RuntimeException("Błąd przy tworzeniu folderu " + e.getMessage());
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
			System.out.println("Czyszczę folder: " + downloadFolder.toAbsolutePath());
			if (Files.exists(downloadFolder) && Files.isDirectory(downloadFolder)) {
				try (var files = Files.list(downloadFolder)) {
					files.forEach(f -> {
						try {
							Files.deleteIfExists(f);
						} catch (IOException e) {
							throw new RuntimeException("Nie udało się usunąć pliku: " + f + " " + e);
						}
					});
				}
			}
		} else {
			((HasDownloads) DriverFactory.getDriver()).deleteDownloadableFiles();
		}
			System.out.println("Wszystkie pliki z folderu pobierania zostały usunięte.");
	}
}
