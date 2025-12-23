package BiDi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.module.Network;
import org.openqa.selenium.bidi.network.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MockApiTest {


	WebDriver driver;
	WebDriverWait wait;
	Network network;
	List<String> interceptIds = new ArrayList<>();

	private final By searchInput = By.id("search-query");
	private final By searchButton = By.cssSelector("button[data-test='search-submit']");
	private final By searchContainer = By.cssSelector("div[data-test='search_completed']");


	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		options.setCapability("webSocketUrl", true);
		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		network = new Network(driver);
	}

	void addIntercept(String host, String path, String term) {
		String encoded = encodeURIComponent(term);
		String search = "?q=" + encoded;

		UrlPattern pattern = new UrlPattern().protocol("https")
				.hostname(host)
				.pathname(path)
				.search(search);

		System.out.println("Przechwytujemy: " + pattern.toMap());

		String id = network.addIntercept(new AddInterceptParameters(InterceptPhase.BEFORE_REQUEST_SENT)
				.urlPattern(pattern));
		interceptIds.add(id);
	}

	private String encodeURIComponent(String term) {
		String enc = URLEncoder.encode(term, StandardCharsets.UTF_8);
		return enc.replace("+", "%20");
	}


	public void mockItemNotFound(String toolName) {
		final String emptyResponse = """
				 {
				 "current_page": 1,
				    "data": [ ],
				    "from": null,
				    "last_page": 1,
				    "per_page": 9,
				    "to": null,
				    "total": 0
				 }
				""";

		addIntercept(
				"api.practicesoftwaretesting.com",
				"/products/search",
				toolName);

		network.onBeforeRequestSent((BeforeRequestSent event) -> {
			RequestData req = event.getRequest();
			String url = req.getUrl();
			String method = req.getMethod();
			boolean blocked = event.isBlocked();
			System.out.println("Złapany event: " + url + " metoda? " + method + " zablokowany? " + blocked);

			if (!blocked) return;

			/*if (url.contains("hammer02.avif")) {
				network.failRequest(event.getRequest().getRequestId());
				System.out.println("[BLOCKED] " + url);
			} else {
				network.continueRequest(
						new ContinueRequestParameters(event.getRequest()
								.getRequestId())
				);
			}*/
			byte[] imageBytes = Base64.getDecoder().decode(emptyResponse);

			String base64 = Base64.getEncoder().encodeToString(imageBytes);



			if ("GET".equalsIgnoreCase(method)) {
				System.out.println("Mamy event do odpowiedzenia");
				ProvideResponseParameters resp = new ProvideResponseParameters(req.getRequestId()).statusCode(200)
						.reasonPhrase("OK")
						/*.headers(List.of(
								new Header("content-type",
										new BytesValue(BytesValue.Type.STRING, "image/avif")),
								new Header("cache-control",
										new BytesValue(BytesValue.Type.STRING, "max-age=14400")),
									new Header(
											"content-length",
											new BytesValue(
													BytesValue.Type.STRING,
													String.valueOf(imageBytes.length)
											)
									)
						))*/

						.headers(List.of(
								new Header("access-control-allow-origin",
										new BytesValue(BytesValue.Type.STRING, "*")),
								new Header("content-type",
										new BytesValue(BytesValue.Type.STRING, "application/json"))
						))
						.body(new BytesValue(BytesValue.Type.BASE64, base64));

				network.provideResponse(resp);
				System.out.println("[BLOCKED] " + method + " " + url);
			} else {
				System.out.println("Kontynuuję");
				network.continueRequest(new ContinueRequestParameters(req.getRequestId()));
			}
		});
	}

	public void performSearch(String term){
		WebElement input = wait.until(ExpectedConditions.elementToBeClickable(searchInput));
		input.click();
		input.sendKeys(term);
		driver.findElement(searchButton).click();

	}

	public void tearDown(){
		try {
			for (String id : interceptIds) network.removeIntercept(id);
			if (network != null) network.close();
		} finally {
			if (driver != null) driver.quit();
		}
	}

	public static void main(String[] args) {
		new MockApiTest().run();
	}

	private void run() {
		setUp();
		try {
			mockItemNotFound("hammer");
			driver.get("https://practicesoftwaretesting.com/");
			performSearch("hammer");
			String resultMsg = wait.until(d -> d.findElement(searchContainer).getText());
			assertThat("There are no products found.").isEqualTo(resultMsg);
		} finally {
			//tearDown();
		}
	}

}
