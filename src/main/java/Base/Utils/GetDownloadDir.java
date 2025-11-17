package Base.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class GetDownloadDir {


	public static Path getDownloadDir() throws IOException {
		Path dir;
		var osProperty = System.getProperty("os.name");
		if (osProperty.equals("Windows 10")) {
			dir = Path.of("DownloadFolder").toAbsolutePath();
			System.out.println("Ustawiam folder pobierania na: " + dir);
		} else {
			dir = Path.of("Home/Downloads").toAbsolutePath();
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
