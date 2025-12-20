package BiDi;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.BiDi;
import org.openqa.selenium.bidi.Command;
import org.openqa.selenium.bidi.Event;
import org.openqa.selenium.bidi.HasBiDi;
import org.openqa.selenium.bidi.module.Network;
import org.openqa.selenium.bidi.network.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.github.dockerjava.api.model.PortConfig.PublishMode.host;

public class NetworkBiDi {


	private static final Logger log = LoggerFactory.getLogger(NetworkBiDi.class);

	public static void main(String[] args) {

	}
		public void testBoniGarcia() {
			Logger logger = LoggerFactory.getLogger(NetworkBiDi.class);
		/*ChromeOptions options = new ChromeOptions();

		options.addArguments("--incognito");
		options.setCapability("webSocketUrl", true);*/

			FirefoxOptions optionsFirefox = new FirefoxOptions();
			optionsFirefox.setCapability("webSocketUrl", true);
			/*ChromeDriver chromeDriver = new ChromeDriver(options);*/
			FirefoxDriver firefoxDriver = new FirefoxDriver(optionsFirefox);

			BiDi bidi = ((HasBiDi) firefoxDriver).getBiDi();
			Network network = new Network(firefoxDriver);
			CompletableFuture<ResponseDetails> future = new CompletableFuture<>();
			firefoxDriver.manage()
					.deleteAllCookies();
			firefoxDriver.manage()
					.timeouts()
					.pageLoadTimeout(Duration.ofSeconds(3));

			network.addIntercept(new AddInterceptParameters(InterceptPhase.RESPONSE_STARTED));
			network.onBeforeRequestSent(event -> {
				System.out.println(">>> WYSŁANO: " + event.getRequest()
						.getUrl());
				System.out.println("    Metoda: " + event.getRequest()
						.getMethod());
			});

			network.onResponseCompleted(event -> {
				System.out.println("<<< ODEBRANO: " + event.getRequest()
						.getUrl());
				System.out.println("    Status: " + event.getRequest()
						.getHeaders());
				System.out.println("    Typ: " + event.getRequest()
						.getRequestId());
				System.out.println("    Metoda: " + event.getRequest()
						.getMethod());
			});

		/*try {
			String urlIntercept = network.addIntercept(new AddInterceptParameters(InterceptPhase.BEFORE_REQUEST_SENT));
			logger.info("Złapałem {}", urlIntercept);
			network.onBeforeRequestSent(event -> {
				String url = event.getRequest()
						.getUrl();
				String id = event.getRequest()
						.getRequestId();
				logger.info("Przechwycono {} + {} ", url, id);
				if (url.contains(".png") | url.contains(".css")) {
					logger.info("Request został zatrzymany");
					network.failRequest(id);
				} else {
					network.continueRequest(new ContinueRequestParameters(id));
				}
			});
		} catch (Exception e){}*/

			Map<String, Object> interceptParams = new HashMap<>();
			interceptParams.put("phases", List.of("beforeRequestSent"));
			interceptParams.put("urlPatterns", Collections.emptyList()); // Nasłuchuj wszystkiego


// Używamy Command<String>, mapper czyta wynik jako Mapę i wyciąga ID
			String interceptId = bidi.send(new Command<>(
					"network.addIntercept",
					interceptParams,
					jsonInput -> {
						// Czytamy odpowiedź jako Mapę
						Map<String, Object> result = jsonInput.read(Map.class);
						// Wyciągamy klucz "intercept"
						return (String) result.get("intercept");
					}
			));

			System.out.println("Intercept ID: " + interceptId);

			// 2. NASŁUCHIWANIE (Używamy surowego stringa nazwy eventu)
			// Musimy rzutować event na właściwą klasę, Selenium robi to automatycznie jeśli użyjemy odpowiedniego mappera
			// Ale najprościej przy surowym podejściu użyć Network.getMapper() jeśli dostępny,
			// LUB po prostu użyć klasy Network do listenera (bo ona działa dobrze), a send() tylko do sterowania.

			// 1. SUBSKRYPCJA (Niezbędna)
		/*Map<String, Object> subscribeParams = new HashMap<>();
		subscribeParams.put("events", List.of("network.beforeRequestSent"));*/
			//bidi.send(new Command<>("session.subscribe", subscribeParams, json -> null));


			log.info("Test 1");
			// Definicja zdarzenia jako MAPA (działa zawsze)
			Event<Map<String, Object>> RAW_NETWORK_EVENT = new Event<>(
					"network.beforeRequestSent",
				/*jsonInput -> {
					log.info("Test 2");
					jsonInput.values().forEach(e -> logger.info("{}", e));
					return jsonInput;
				}*/
					(Map<String, Object> eventData) -> {
						// eventData to już gotowa mapa z danymi zdarzenia!
						log.info("Test 2 - Odebrano zdarzenie: {}", eventData);

						// Po prostu zwracamy to co dostaliśmy
						return eventData;
					}

			);


			bidi.addListener(RAW_NETWORK_EVENT, map -> {
				// Musimy ręcznie "kopać" w mapie
				// Struktura: { "request": { "url": "...", "requestId": "..." }, ... }

				Map<String, Object> requestMap = (Map<String, Object>) map.get("request");
				String url = (String) requestMap.get("url");
				String requestId = (String) requestMap.get("request");


				System.out.println("Złapano URL: " + url);
				System.out.println("Złapano request: " + requestId);

				new Thread(() -> {
					try {
						if (url.contains(".css")) {
							Map<String, Object> params = new HashMap<>();
							params.put("request", requestId);
							params.put("errorText", "unknown error");
							bidi.send(new Command<>("network.failRequest", params, json -> null));
						} else {
							Map<String, Object> params = new HashMap<>();
							params.put("request", requestId);
							bidi.send(new Command<>("network.continueRequest", params, json -> null));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}).start();
			});

			firefoxDriver.get("https://bonigarcia.dev/selenium-webdriver-java/");


			network.onBeforeRequestSent((BeforeRequestSent event) -> {
				RequestData req = event.getRequest();

			});
		}







			/*
		try (Network network = new Network(chromeDriver)) {
			// Nasłuchuj interceptów
			network.addIntercept(new AddInterceptParameters(InterceptPhase.BEFORE_REQUEST_SENT));

			network.onBeforeRequestSent(event -> {
				// Całą logikę obsługi przekazujemy do ExecutorService,
				// która zarządza wątkami i zapewnia nieblokującą obsługę.
				networkExecutor.submit(() -> {
					String url = event.getRequest().getUrl();
					String id = event.getRequest().getRequestId();

					try {
						if (url.contains(".css")) {
							System.out.println("BLOKOWANIE CSS: " + url);
							// Użycie FailRequestParameters
							network.failRequest(id);
						} else {
							// Użycie ContinueRequestParameters
							network.continueRequest(new ContinueRequestParameters(id));
						}
					} catch (Exception e) {
						System.err.println("Błąd w obsłudze requestu " + url + ": " + e.getMessage());
						// WAŻNE: W przypadku błędu (np. timeoutu) często trzeba podjąć próbę
						// kontynuacji, aby nie zablokować innych żądań.
						try {
							network.continueRequest(new ContinueRequestParameters(id));
						} catch (Exception ignored) {
							// Ignore
						}
					}
				}); // Koniec .submit()
			});

			// 2. Wczytanie strony musi nastąpić PO ustawieniu wszystkich listenerów.
			chromeDriver.get("https://getbootstrap.com/docs/5.1/examples/dashboard/");

			// Dodaj oczekiwanie, aby dać czas na przetworzenie wszystkich żądań
			// Thread.sleep(5000);

		} finally {
			// 3. Pamiętaj, aby ZAMKNĄĆ ExecutorService po zakończeniu testu
			networkExecutor.shutdownNow();
		}*/

		/*FirefoxOptions options = new FirefoxOptions();
		options.setCapability("webSocketUrl", true);
		FirefoxDriver driver = new FirefoxDriver(options);*/

		/*try (Network network = new Network(driver)) {
			network.addIntercept(new AddInterceptParameters(InterceptPhase.BEFORE_REQUEST_SENT));
			network.onBeforeRequestSent(
					responseDetails -> network.failRequest(responseDetails.getRequest().getRequestId()));
			driver.manage().timeouts().pageLoadTimeout(Duration.of(5, ChronoUnit.SECONDS));
			Assert.assertThrows(TimeoutException.class, () -> driver.get("https://the-internet.herokuapp.com/basic_auth"));
		}*/

		/*try (Network network = new Network(driver)) {
			network.addIntercept(new AddInterceptParameters(InterceptPhase.BEFORE_REQUEST_SENT));
			network.onBeforeRequestSent(event -> {
						// Całą logikę obsługi przekazujemy do ExecutorService,
						// która zarządza wątkami i zapewnia nieblokującą obsługę.

						String url = event.getRequest()
								.getUrl();
						String id = event.getRequest()
								.getRequestId();

						try {
							if (url.contains(".png")) {
								System.out.println("BLOKOWANIE PNG: " + url);
								// Użycie FailRequestParameters
								network.failRequest(id);
							} else {
								// Użycie ContinueRequestParameters
								network.continueRequest(new ContinueRequestParameters(id));
							}
						} catch (Exception e) {
							System.err.println("Błąd w obsłudze requestu " + url + ": " + e.getMessage());
							// WAŻNE: W przypadku błędu (np. timeoutu) często trzeba podjąć próbę
							// kontynuacji, aby nie zablokować innych żądań.
						}
					});
			driver.manage().timeouts().pageLoadTimeout(Duration.of(5, ChronoUnit.SECONDS));
			driver.get("https://bonigarcia.dev/selenium-webdriver-java/");*/








			/*network.onBeforeRequestSent(
					responseDetails -> network.failRequest(responseDetails.getRequest().getRequestId()));
			driver.manage().timeouts().pageLoadTimeout(Duration.of(5, ChronoUnit.SECONDS));
			Assert.assertThrows(TimeoutException.class, () -> driver.get("https://the-internet.herokuapp.com/basic_auth"));*/
}




		//Geolokacja!!!!!!!!!!!!!!!!
//		bidi.send(Emulation.setGeolocationOverride(4.323, 4.53))

//		bidi.addListener(new Network(chromeDriver), e -> chromeDriver.network().add












