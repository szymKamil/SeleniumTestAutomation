package BiDi;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.BiDi;
import org.openqa.selenium.bidi.Connection;
import org.openqa.selenium.bidi.HasBiDi;
import org.openqa.selenium.bidi.module.LogInspector;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v142.network.Network;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

public class BiDiTest {


	@Test
	public void bidiTest() throws MalformedURLException, InterruptedException {
		/*ChromeOptions options = new ChromeOptions();
		 *//*options.addArguments("");*//*
		options.setCapability("webSocketUrl", true);
		URL gridUrl = URI.create("http://localhost:4444").toURL();
		WebDriver driver = new RemoteWebDriver(gridUrl, options);

		// 3. Augmentacja (Kluczowy moment!)
		// RemoteWebDriver sam z siebie jest "głupi" i nie wie, że może obsługiwać BiDi.
		// Augmenter dodaje mu te supermoce (interfejs HasBiDi).
		driver = new Augmenter().augment(driver);
		// 4. Uzyskanie dostępu do BiDi
		// Teraz możemy bezpiecznie rzutować drivera na HasBiDi
		if (driver instanceof HasBiDi) {
			try (BiDi biDi = ((HasBiDi) driver).getBiDi()) {

				// PRZYKŁAD: Nasłuchiwanie logów przeglądarki (zamiast Network, co jest prostsze na start)
				LogInspector logInspector = new LogInspector((WebDriver) biDi);
				logInspector.onConsoleEntry(log -> {
					System.out.println("LOG Z PRZEGLĄDARKI: " + log.getText());
				});

				// Tutaj twoja nawigacja
				driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");

				// Aby zobaczyć efekt, czasem trzeba chwilę poczekać
				Thread.sleep(2000);
			}
		} else {
			System.out.println("Driver nie obsługuje BiDi po augmentacji.");
		}

		driver.quit();*/

		ChromeOptions options = new ChromeOptions();
		// CDP nie wymaga capability "webSocketUrl" w ten sam sposób co BiDi,
		// ale Grid musi na to pozwalać.
		options.addArguments("--remote-debugging-port=0",
				"--disable-web-security",
				"--disable-features=VizDisplayCompositor");
		options.setCapability("se:options", Map.of("bidi", true));
		options.addArguments("--remote-allow-origins=*");


		//WebDriver driver = new RemoteWebDriver(URI.create("http://host.docker.internal:4444").toURL(), options);
		// WebDriver driver = new ChromeDriver(options);
		//WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),options);


		try {
			WebDriver driver = new RemoteWebDriver(
					new URL("http://localhost:4444/wd/hub"), // Użyj localhost i dodaj /wd/hub
					options);

			driver = new Augmenter().augment(driver);
			// Testowanie narzędzi deweloperskich (Dev Tools) wymaga dodatkowej konfiguracji
			// Zwykle jest to realizowane poprzez ChromeOptions lub inne mechanizmy...

			if (driver instanceof HasDevTools) {
				DevTools devTools = ((HasDevTools) driver).getDevTools();
				devTools.createSession();

				// 3. Włączenie sieci
				devTools.send(org.openqa.selenium.devtools.v142.network.Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));

				// 4. Nasłuchiwanie requestów
				devTools.addListener(Network.requestWillBeSent(), request -> {
					System.out.println("URL: " + request.getRequest()
							.getUrl());
				});

				driver.get("https://google.com");
				Thread.sleep(2000);

				devTools.close();
				driver.quit();


			}


			// 1. Augmentacja
		/*driver = new Augmenter().augment(driver);

		// 2. Rzutowanie na HasDevTools (nie BiDi)
		if (driver instanceof HasDevTools) {
			DevTools devTools = ((HasDevTools) driver).getDevTools();
			devTools.createSession();

			// 3. Włączenie sieci
			devTools.send(org.openqa.selenium.devtools.v142.network.Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));

			// 4. Nasłuchiwanie requestów
			devTools.addListener(Network.requestWillBeSent(), request -> {
				System.out.println("URL: " + request.getRequest().getUrl());
			});

			driver.get("https://google.com");
			Thread.sleep(2000);

			devTools.close();
		} else {
			System.out.println("DevTools nie są dostępne na tym Gridzie.");
		}

		driver.quit(); */

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}






