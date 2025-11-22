package Base.Utils;

import Base.Drivers.DriverFactory;
import org.openqa.selenium.HasDownloads;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GetDownloadDir {


	/***
	 * Metoda weryfikuje środowisko, na którym wykonywany jest test. W zależności od parametru, folder zapisu plików określany jest:
	 * a) w przypadku lokalnego PC, w głównym projekcie,
	 * b) w przypadku innego systemu, np. Linux, przyjmowane jest zdalne uruchomienie kontenerowe w Dokerze, przez co konfigurowany jest domyślny katalog pobierania,.
	 * W przypadku nieznalezienia katalogu jest on tworzony.
	 */
	public static boolean testIsRunningInDocker() {
		boolean dockerEnv = Boolean.parseBoolean(System.getProperty("dockerEnviroment", "false"));
		System.out.println("DockerEnv ma wartość: " + dockerEnv);
		return dockerEnv;
	}

	public static Path getDownloadDirectory(){
		boolean dockerEnv = testIsRunningInDocker();
		Path dir = null;
		if (!dockerEnv) {
			dir = Path.of("DownloadFolder").toAbsolutePath();
			System.out.println("Test uruchomiony lokalnie. Ustawiam folder pobierania na: " + dir);
		} /*else {
			dir = Path.of("/home/seluser/Downloads/");
			System.out.println("Test uruchamiany jest zdalnie. Ustawiam folder pobierania na: " + dir);
		}*/
		/*try {
			if (!Files.exists(dir)) {
				Files.createDirectories(dir);
			} else {
				System.out.println("Folder istnieje");
			}
		} catch (Exception e) {
			throw new RuntimeException("Błąd przy tworzeniu folderu " + e.getMessage());
		}*/
		return dir;
	}

	public static void clearDownloadFolderFromFiles() throws IOException {
		boolean isDocker = testIsRunningInDocker();
		if (!isDocker) {
			var downloadFolder = getDownloadDirectory();
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
			}
		} else {
			((HasDownloads) DriverFactory.getDriver()).deleteDownloadableFiles();
		}
			System.out.println("Wszystkie pliki z folderu pobierania zostały usunięte.");
	}
}
