package Base.Utils;

import Base.Drivers.DriverFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class GetDownloadDir {


	/***
	 * Metoda weryfikuje system operacyjny, na którym wykonywany jest test. W zależności od tego folder zapisu plików określany jest
	 * a) w przypadku Windowsa w lokalnym projekcie, gdyż zakładane jest lokalne uruchomienie,
	 * b) w przypadku innego systemu, np. Linux, przyjmowane jest zdalne uruchomienie kontenerowe w Dokerze, przez co katalog jest inny.
	 * W przypadku nieznalezienia katalogu jest on tworzony.
	 */
	public static Path getDownloadDir()  {
		Path dir;
		var url = DriverFactory.getUrl();
		if (url == null) {
			dir = Path.of("DownloadFolder").toAbsolutePath();
			System.out.println("Ustawiam folder pobierania na: " + dir);
		} else {
			System.out.println("Test uruchamiany jest zdalnie na URL: " + url);
			dir = Path.of("/home/seluser/Downloads");
			System.out.println("Ustawiam folder pobierania na: " + dir);
		}
		try {
			if (!Files.exists(dir)) {
				Files.createDirectories(dir);
			} else {
				System.out.println("Folder istnieje");
			}
		} catch (Exception e) {
			throw new RuntimeException("Błąd przy tworzeniu folderu " + e.getMessage());
		}
		System.out.println("Ścieżka to: " + dir);
		return dir;
	}

	public static void clearDownloadFolder() throws IOException {
		var downloadFolder = getDownloadDir();
		System.out.println("Czyszczę folder: " + downloadFolder.toAbsolutePath());
		if (Files.exists(downloadFolder) && Files.isDirectory(downloadFolder)) {
			try (var files = Files.list(downloadFolder)) {
				files.forEach(f -> {
					try {
						Files.deleteIfExists(f);
					} catch (IOException e) {
						throw new RuntimeException("Nie udało się usunąc pliku: " + f + " " + e);
					}
				});
			}
			System.out.println("Wszystkie pliki z folderu pobierania zostały usunięte.");
		}
	}
}
