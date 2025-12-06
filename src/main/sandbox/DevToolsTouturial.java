import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v142.domstorage.DOMStorage;
import org.openqa.selenium.devtools.v142.domstorage.model.Item;
import org.openqa.selenium.devtools.v142.domstorage.model.StorageId;
import org.openqa.selenium.devtools.v142.target.Target;
import org.openqa.selenium.devtools.v142.target.model.TargetID;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static java.lang.invoke.MethodHandles.lookup;

public class DevToolsTouturial {
	ChromeOptions options = new ChromeOptions();


	RemoteWebDriver driver;
	WebDriverWait wait;
	Logger logger = LoggerFactory.getLogger(lookup().lookupClass());
	Actions actions;
	Random random = new Random(4);
	JavascriptExecutor js;
	LocalDateTime localDateTime = LocalDateTime.now();
	DevTools devTools;


	@BeforeClass
	public void webDriverInitialize() {
		WebDriverManager.chromedriver()
				.setup();
		options.addArguments("--kiosk");
		URL url;
		try {
			url = URI.create("http://localhost:4444")
					.toURL();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		driver = new RemoteWebDriver(url, options);
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		js = (JavascriptExecutor) driver;
		devTools = ((HasDevTools) driver).getDevTools();
	}

	@Test
	public void test0000() {
		ChromeDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

// Krok 1: Utwórz sesję i włącz potrzebne domeny na samym początku.
		devTools.createSession();
		devTools.send(DOMStorage.enable());

// Krok 2: Nawiguj na stronę.
		String initialUrl = "https://bonigarcia.dev/selenium-webdriver-java/web-storage.html";
		driver.get(initialUrl);

// Krok 3: Użyj jawnego oczekiwania, aby upewnić się, że strona jest interaktywna.
// To dobry pierwszy krok do synchronizacji.
		WebElement header = driver.findElement(By.xpath("//h1[text()='Web storage']"));
		wait.until(ExpectedConditions.visibilityOf(header));

// Krok 4 (NAJWAŻNIEJSZY): Zbuduj StorageId DOPIERO TERAZ, używając aktualnego stanu przeglądarki.
		String currentUrl = driver.getCurrentUrl();
		System.out.println("Używam aktualnego URL do stworzenia StorageId: " + currentUrl);

		URI uri = URI.create(currentUrl);
		String origin = uri.getScheme() + "://" + uri.getHost();
		if (uri.getPort() != -1) {
			origin += ":" + uri.getPort();
		}

		StorageId localStorageId = new StorageId(
				Optional.of(initialUrl), // <--- TO JEST KLUCZOWE
				Optional.empty(),
				true
		);

		List<Item> items = devTools.send(DOMStorage.getDOMStorageItems(localStorageId));
		for (Item item : items) {
			System.out.println("Key: " + item + " | Value: " + item);
		}


//        devTools.send(DOMStorage.domStorageItemAdded();
// Krok 5: Wykonaj operację CDP.
// Umieszczenie tego w bloku try-catch pomoże w dalszym debugowaniu.
		try {
			devTools.send(DOMStorage.setDOMStorageItem(localStorageId, "klucz_cdp", "wartosc_cdp"));
			System.out.println("Operacja CDP na LocalStorage zakończona sukcesem.");
		} catch (Exception e) {
			System.err.println("BŁĄD podczas operacji CDP! Analiza:");
			System.err.println("Użyty URL: " + currentUrl);
			System.err.println("Wyjątek: " + e.getMessage());
			// Jeśli błąd nadal występuje, możesz dodać tu małą pauzę (tylko do debugowania!)
			// aby sprawdzić, czy to na pewno race condition.
			// Thread.sleep(500);
		}


	}


	@Test
	public void test213123124() throws InterruptedException {
		try {
			// Utwórz sesję DevTools na początku
			devTools.createSession();

			// --- Krok 1: Otwórz stronę główną i zapamiętaj jej uchwyt ---
			driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
			String originalTabHandle = driver.getWindowHandle();
			System.out.println("Otwarto oryginalną zakładkę: " + driver.getTitle());

			// --- Krok 2: Otwórz nową zakładkę i przejdź na inną stronę ---
			driver.switchTo()
					.newWindow(WindowType.TAB);
			driver.get("https://www.selenium.dev/documentation/");
			System.out.println("Otwarto nową zakładkę: " + driver.getTitle());

			// --- Krok 3: Wróć do pierwotnej zakładki, aby pracować w jej kontekście ---
			driver.switchTo()
					.window(originalTabHandle);
			System.out.println("Powrócono do zakładki: " + driver.getTitle());
			Thread.sleep(1000); // Pauza tylko dla demonstracji

			// --- Krok 4: Pobierz listę wszystkich targetów za pomocą CDP ---
			// Ta komenda zwraca listę obiektów `TargetInfo` z pakietu `idealized`
			var allTargets = devTools.send(Target.getTargets(Optional.empty()));


			System.out.println("\n--- Znaleziono " + allTargets.size() + " aktywnych targetów ---");
			for (var info : allTargets) {
				System.out.println("  > Target ID: " + info.getTargetId());
				System.out.println("    Typ: " + info.getType());
				System.out.println("    Tytuł: " + info.getTitle());
				System.out.println("    URL: " + info.getUrl());
			}
			System.out.println("-------------------------------------\n");

			// --- Krok 5: Znajdź interesujący nas target (zakładkę z dokumentacją Selenium) ---
			Optional<org.openqa.selenium.devtools.v142.target.model.TargetInfo> seleniumDocTargetInfo = allTargets.stream()
					.filter(info -> info.getTitle()
							.contains("Selenium") && info.getType()
							.equals("page"))
					.findFirst();

			// --- Krok 6: Wykonaj operacje na znalezionym targecie ---
			if (seleniumDocTargetInfo.isPresent()) {
				org.openqa.selenium.devtools.v142.target.model.TargetInfo targetToActivate = seleniumDocTargetInfo.get();
				System.out.println("Znaleziono target do aktywacji: " + targetToActivate.getTitle());

				// --- ROZWIĄZANIE PROBLEMU: Konwersja z `idealized` na `vXXX` ---
				// 1. Pobierz ID z obiektu `idealized.TargetInfo`
				TargetID idealizedId = targetToActivate.getTargetId();

				// 2. Utwórz nowy obiekt `vXXX.TargetID` używając wartości string z poprzedniego
				org.openqa.selenium.devtools.v142.target.model.TargetID versionedId =
						new org.openqa.selenium.devtools.v142.target.model.TargetID(idealizedId.toString());

				// 3. Użyj WERSJONOWANEGO ID do wykonania komend
				System.out.println("\nAktywowanie targetu za pomocą ID: " + versionedId);
				devTools.send(Target.activateTarget(versionedId));
				Thread.sleep(2000); // Pauza dla demonstracji

				// Weryfikacja
				System.out.println("Aktywna zakładka po przełączeniu przez CDP: " + driver.getTitle());
				assert driver.getTitle()
						.contains("Selenium");

				System.out.println("\nZamykanie targetu za pomocą ID: " + versionedId);
				devTools.send(Target.closeTarget(versionedId));
				Thread.sleep(2000); // Pauza dla demonstracji

				System.out.println("Target został zamknięty.");

			} else {
				System.out.println("Nie znaleziono docelowego targetu.");
			}

		} finally {
			// Blok finally gwarantuje, że przeglądarka zostanie zamknięta,
			// nawet jeśli w bloku try wystąpi błąd.
			if (driver != null) {
				System.out.println("\nZamykanie przeglądarki w bloku finally...");
				driver.quit();
			}

		}
	}
}