package Selenium.BoniGarcia;

import Base.Drivers.DriverFactory;
import Base.Utils.FileDownloadUtils;
import Base.Utils.Screenshot;
import POM.WebTest.BoniGarcia.Pages.*;
import POM.WebTest.BoniGarcia.BaseTest.BaseTest;
import POM.WebTest.BoniGarcia.Utils.DropdownOptions;
import POM.WebTest.BoniGarcia.Utils.PointForCanvas;
import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import io.qameta.allure.testng.Tag;
import org.openqa.selenium.support.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import org.testng.annotations.Optional;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


import static org.assertj.core.api.Assertions.assertThat;

@Listeners({AllureTestNg.class})
public class MainTest extends BaseTest {


	private static final Logger log = LoggerFactory.getLogger(MainTest.class);

	/**
	 * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 * weryfikację adresu URL oraz występowania głównych elementów na stronie.
	 */
	@Test(priority = 0, groups = {"interface", "regression"})
	@Epic("Boni Garcia WebPageTest")
	@Story("Main page verification")
	@Description("Weryfikacja, czy strona logowania posiada wszystkie elementy.")
	@Owner("Kamil")
	@Tag("Interface")
	@Severity(SeverityLevel.BLOCKER)
	public void mainPageTestElementsVerification() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.verifyHomePageContent();
		log.info("Test mainPageTestElementsVerification ukończony");
	}

	/**
	 * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 * weryfikację tekstu nagłówka, następnie przejście do podstrony Web form,  na której przetestowane zostanie możliwość wypełnienia, weryfikacji i przesłania formularza oraz upewnienie się,
	 * że formularz został poprawnie przesłany poprzez weryfikację wyświetlania odpowiedniego komunikatu o sukcesie.
	 */
	@Test(priority = 2, groups = {"functional", "regression"}, dependsOnMethods = {"mainPageTestElementsVerification"})
	@Epic("Boni Garcia WebPageTest")
	@Story("Web form verification")
	@Description("Weryfikacja, czy w formularzu można uzupełnić wszystkie pola, a na końcu przesłać całość.")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Kamil")
	@Parameters("path")
	public void webFormTest(@Optional("src/test/resources/f-vat_2011.pdf") String file)  {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Web form");
		WebForm webForm = new WebForm(DriverFactory.getDriver(), DriverFactory.getWait());
		webForm.verifyAbstractPage();
		webForm.fillTextInput();
		webForm.fillPasswordInput();
		webForm.fillTextAreaInput();
		webForm.selectElementOnDropdownList("Two");
		webForm.verifyDisabledFields();
		webForm.selectElementOnDataList(DropdownOptions.OPTION_B);
		webForm.uploadFile(file);
		webForm.checkboxSelector();
		webForm.radioSelector(2);
		webForm.colorPicker(new Color(65, 45, 34, 2).asHex());
		webForm.dateSetter(LocalDate.now());
		webForm.changeRange();
		webForm.submitForm();
		log.info("Test webFormTest ukończony");
	}

	/**
	 * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 * weryfikację tekstu nagłówka, następnie przejście do podstrony Navigation, a następnie weryfikację działania przycisków i weryfikację
	 * zmieniającej się treści w paragrafach.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Web form verification")
	@Description("Weryfikacja, czy na stronie z nawigacją można przechodzić pomiędzy poszczególnymi podstronami za pomocą przycisków.")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Kamil")
	@Test(priority = 3, dependsOnMethods = {"mainPageTestElementsVerification"}, groups = "interface")
	public void navigationPageTest() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Navigation");
		NavigationPage navigationPage = new NavigationPage(DriverFactory.getDriver(), DriverFactory.getWait());
		navigationPage.verifyAbstractPage();
		navigationPage.verifyBtns();
	}

	/**
	 * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 * weryfikację tekstu nagłówka, następnie przejście do podstrony Dropdown menu, i użycie wybranych dropdownów razem z kliknięciem odpowiednich przycisków w menu.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Dropdown Menu Test")
	@Description("Weryfikacja działania pól z dropdownem.")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Kamil")
	@Test(priority = 2, groups = "interface")
	public void dropdownMenuTest() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Dropdown menu");
		DropdownMenuPage dropdownMenu = new DropdownMenuPage(DriverFactory.getDriver(), DriverFactory.getWait());
		dropdownMenu.verifyAbstractPage();
		dropdownMenu.openDropOneAndPick(1);
		dropdownMenu.openDropTwoAndPick(2);
		dropdownMenu.openDropThreeAndPick(3);

	}
	/***
	 * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 * weryfikację tekstu nagłówka, następnie przejście do podstrony Mouse over i weryfikacje podpisów pod rysunkami,
	 * które widoczne są dopiero po umieszczeniu kursora na obrazkach.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Mouse Over Test")
	@Description("Weryfikacja, czy umieszczenie kursora na elementach poprawie wyświetla hinty pod nimi.")
	@Severity(SeverityLevel.NORMAL)
	@Owner("Kamil")
	@Test(priority = 3)
	public void mouseOverTest() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Mouse over");
		MouseOverPage mouseOverPage = new MouseOverPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mouseOverPage.verifyAbstractPage();
		mouseOverPage.hoverOverImg(2);
		mouseOverPage.choseAndWaitFormElementToBeVisible("Award");
		mouseOverPage.verifyImgCaptions();
	}

	/**
	 * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 * weryfikację tekstu nagłówka, i następnie przejście do podstrony Drag and drop, na której test przeniesie element do miejsca docelowego.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Drag and Drop Test")
	@Description("Test sprawdza działanie mechanizmu drag and drop.")
	@Severity(SeverityLevel.MINOR)
	@Owner("Kamil")
	@Test(priority = 3, groups = {"interface"})
	public void dragAndDropTest() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Drag and drop");
		DragAndDropPage dragAndDrop = new DragAndDropPage(DriverFactory.getDriver(), DriverFactory.getWait());
		dragAndDrop.verifyAbstractPage();
		dragAndDrop.getElementCoords();
		dragAndDrop.getTargetCoords();
		dragAndDrop.dragPanelTo(dragAndDrop.getTargetCoords());
		dragAndDrop.getElementCoords();
		dragAndDrop.getTargetCoords();

	}
	/**
	 * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 * weryfikację tekstu nagłówka, i następnie przejście do podstrony Draw in canvas, na której zostanie użyte płótno do narysowania przykłądowego kształtu.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Canvas test")
	@Description("Test sprawdza działanie mechanizmu rysowania na płótnie.")
	@Severity(SeverityLevel.MINOR)
	@Owner("Kamil")
	@Test(priority = 4, groups = {"interface"})
	public void canvasTest() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Draw in canvas");
		CanvasPage canvasPage = new CanvasPage(DriverFactory.getDriver(), DriverFactory.getWait());
		canvasPage.verifyAbstractPage();
		canvasPage.paintInCanvas();
		canvasPage.paintInCanvas(new PointForCanvas(50, 20), new PointForCanvas(30, 10), new PointForCanvas(-20, -15));
	}

	/**
	 * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 * weryfikację tekstu nagłówka, i następnie przejście do podstrony Loading images a następnie weryfikację, czy obrazki zostaną wyświetlone.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Image loading test")
	@Description("Test sprawdza czy rysunki na stronie poprawnie się ładują.")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Kamil")
	@Test(priority = 2, groups = {"interface"})
	public void loadingImages() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Loading images");
		LoadingImagesPage loadingImagesPage = new LoadingImagesPage(DriverFactory.getDriver(), DriverFactory.getWait());
		loadingImagesPage.verifyAbstractPage();
		loadingImagesPage.waitForImagesToLoad();
		loadingImagesPage.confirmElementsVisibility();
	}

	/**
	 *Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 *weryfikację tekstu nagłówka, i następnie przejście do podstrony Slow calculator, i użycie kalkulatora
	 do wykonania obliczeń.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Slow calculator test")
	@Description("Test sprawdza czy czy wolny kalkulator działa poprawnie.")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Kamil")
	@Test(priority = 1, groups = {"business"})
	public void slowCalculatorTest() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Slow calculator");
		SlowCalculator slowCalculator = new SlowCalculator(DriverFactory.getDriver(), DriverFactory.getWait());
		slowCalculator.verifyAbstractPage();
		slowCalculator.verifyAbstractPage();
		slowCalculator.setCalcDelay(3);
		slowCalculator.useCalculator("2+3=");
		slowCalculator.getResultOfCalc();
	}

	/**
	 *Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 *weryfikację tekstu nagłówka, i następnie przejście do podstrony Long page, i przescrollowanie jej na sam
	 koniec.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Long page test")
	@Description("Test sprawdza czy strona poprawie się przewija.")
	@Severity(SeverityLevel.NORMAL)
	@Owner("Kamil")
	@Test(priority = 3, groups = {"interface"})
	public void longPageTest() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Long page");
		LongPage longPage = new LongPage(DriverFactory.getDriver(), DriverFactory.getWait());
		longPage.verifyAbstractPage();
		longPage.scrollToLastParagraph();
		longPage.getTextFromParagraph(3);
		longPage.printAllParagraphs();

	}

	/**
	 *Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 *weryfikację tekstu nagłówka, i następnie przejście do podstrony Infinite scroll, a następnie przescrollowanie
	 strony.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Infinite scroll")
	@Description("Test sprawdza czy nieskończona strona pozwala bez końća przewijać stronę.")
	@Severity(SeverityLevel.NORMAL)
	@Owner("Kamil")
	@Test(priority = 3, groups = {"interface"})
	public void infiniteScrollTest() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Infinite scroll");
		InfiniteScrollPage infiniteScroll = new InfiniteScrollPage(DriverFactory.getDriver(), DriverFactory.getWait());
		infiniteScroll.verifyAbstractPage();
		log.info("Początkowa wysokość strony to: {}", infiniteScroll.getPageHeight());
		infiniteScroll.scrollXTimes();
		log.info("Końcowa wysokość strony to: {}", infiniteScroll.getPageHeight());
	}

	/**
	 *Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 *weryfikację tekstu nagłówka, i następnie przejście do podstrony Shadow DOM i odczytanie wartości z elementu
	 ukrytego w shadow DOM.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Shadow Root")
	@Description("Test sprawdza czy można dostać się do ShadowRoot strony.")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Kamil")
	@Test(priority = 2, groups = {"interface", "shadowRoot"})
	public void shadowRootTest() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Shadow DOM");
		ShadowDomPage shadowDomPage = new ShadowDomPage(DriverFactory.getDriver(), DriverFactory.getWait());
		shadowDomPage.verifyAbstractPage();
		shadowDomPage.getShadowRootContent();
	}

	/**
	 *Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 *weryfikację tekstu nagłówka, i następnie przejście do podstrony Cookies, następnie pobranie domyślnych
	 wartości ciasteczek,
	 *modyfikacja ich i weryfikacja, czy ciasteczka się zmieniły.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Shadow Root")
	@Description("Test sprawdza czy można dostać się do ShadowRoot strony.")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Kamil")
	@Test(priority = 2, groups = {"interface", "shadowRoot"})
	public void cookiesPageTest() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Cookies");
		CookiesPage cookiesPage = new CookiesPage(DriverFactory.getDriver(), DriverFactory.getWait());
		cookiesPage.verifyAbstractPage();
		cookiesPage.refreshCookiesBtn();
		cookiesPage.getCookiesText();
		cookiesPage.deleteAllCookies();
		cookiesPage.addCookie("Username", "RobertZamojski");
		cookiesPage.addCookie("data", LocalDateTime.now()
				.format(DateTimeFormatter.ISO_DATE));
		cookiesPage.refreshCookiesBtn();
		cookiesPage.getCookiesText();
	}

	/**
	 *Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 *weryfikację tekstu nagłówka, i następnie przejście do podstrony Frames, a następnie weryfikację
	 widoczności elementów ukrytych w
	 *różnych sekcjach i sprawdzenie możliwości przełączania się pomiędzy sekcjami strony.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Frames test")
	@Description("Test sprawdza czy można przełączać się na stronie pomiędzy ramkami.")
	@Severity(SeverityLevel.NORMAL)
	@Owner("Kamil")
	@Test(priority = 4, groups = {"interface"})
	public void framesPageTest() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Frames");
		FramesPage framesPage = new FramesPage(DriverFactory.getDriver(), DriverFactory.getWait());
		framesPage.switchToFrame("header");
		framesPage.verifyMainHeader();
		framesPage.verifyImage();
		framesPage.switchToFrame("footer");
		framesPage.verifyCopyrights();
		framesPage.switchToFrame("body");
		framesPage.verifyVisibilityOfParagraphs();
	}

	/**
	 *Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 *weryfikację tekstu nagłówka, i następnie przejście do podstrony IFrames, przescrollowanie i weryfikację
	 tekstu w ostatnim paragrafie.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("iFrames test")
	@Description("Test sprawdza czy można przełączać się pomiędzy ramkami.")
	@Severity(SeverityLevel.NORMAL)
	@Owner("Kamil")
	@Test(priority = 4, groups = {"interface", "shadowRoot"})
	public void iFramesPageTest() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("IFrames");
		IFramePage iFramePage = new IFramePage(DriverFactory.getDriver(), DriverFactory.getWait());
		iFramePage.verifyAbstractPage();
		iFramePage.switchToiFrame();
		iFramePage.scrollPage();
	}

	/** Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 *weryfikację tekstu nagłówka, i następnie przejście do podstrony Dialog boxes, następnie uruchomienie każdego
	 z aletrów i weryfikację poprawności ich wyświetlania i działania, w kontekście przesłanego tekstu.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Alert test")
	@Description("Test sprawdza czy alerty działają poprawnie.")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Kamil")
	@Test(priority = 3, groups = {"interface"})
	public void alertPageTest() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Dialog boxes");
		DialogBoxesPage dialogBoxesPage = new DialogBoxesPage();
		dialogBoxesPage.verifyAbstractPage();
		dialogBoxesPage.launchAlert();
		dialogBoxesPage.launchConfirm();
		assertThat(dialogBoxesPage.getTextFromConfirm("ok")).isEqualTo("Is this correct?");
		assertThat(dialogBoxesPage.getTextFromConfirmParagraph()).isEqualTo("You chose: true");
		String promptText = "To jest prompt :)";
		dialogBoxesPage.launchPrompt();
		dialogBoxesPage.setTextToPromptAlert(promptText);
		assertThat(dialogBoxesPage.getTextFromPrompt()).isEqualTo("You typed: " + promptText);
		dialogBoxesPage.handleModalWindow("save");
		assertThat(dialogBoxesPage.getTextFromModal()).isEqualTo("You chose: Save changes");
	}


	/**
	 *Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 *weryfikację tekstu nagłówka, i następnie przejście do podstrony Web storage, następnie wyczyszczenie lokalnej
	 pamięci przeglądarki i dodanie nowych kluczy,
	 *a na końcu weryfikacja ich widoczności.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("WebStorage")
	@Description("Test sprawdza działanie magazynu danych na stronie (pamięci podręcznej).")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Kamil")
	@Test(groups = {"devTools"}, priority = 1)
	public void webStoragePageTests() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Web storage");
		WebStoragePage webStoragePage = new WebStoragePage(DriverFactory.getDriver(), DriverFactory.getWait());
		webStoragePage.verifyAbstractPage();
		webStoragePage.clearLocalStorage();
		webStoragePage.clearSessionStorage();
		webStoragePage.inputValueToLocalStorage("Klucz testowy sesji lokalnej 1", "Wartość testowa");
		webStoragePage.inputValueToLocalStorage("Klucz testowy sesji lokalnej 2", "Wartość testowa");
		webStoragePage.inputValueToSessionStorage("Klucz testowy pamięci sesji 1", "Wartość testowa sesji");
		webStoragePage.inputValueToSessionStorage("Klucz testowy pamięci sesji 2", "Wartość testowa sesji");
		webStoragePage.clickLocalStorageBtn();
		webStoragePage.clickLocalSessionBtn();
		log.info("Local storage posiada wartości: {}", webStoragePage.getTextFormStorageParagraph());
		log.info("Session storage posiada wartości: {}", webStoragePage.getTextFormSessionParagraph());
	}

	/**
	 *Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 *weryfikację tekstu nagłówka, i następnie przejście do podstrony Geolocation, następnie zmianę ustawień
	 przeglądarki i zmianę informacji o geolokacji, miejscu przeglądarki,
	 *i weryfikację widoczności tych zmian.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Geolocation")
	@Description("Test sprawdzający mechanizm geolokacji.")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Kamil")
	@Test(groups = {"devTools"}, priority = 1)
	public void geolocationTest() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Geolocation");
		GeolocationPage geolocationPage = new GeolocationPage(DriverFactory.getDriver(), DriverFactory.getWait());
		geolocationPage.verifyAbstractPage();
		geolocationPage.setCoordinates(4.323, 4.53);
		geolocationPage.clickGeolocationBtn();
		log.info("Koordynaty to: {}", geolocationPage.returnCoordinates());
		assertThat(geolocationPage.returnCoordinates()
				.contains("4.323")).isTrue();
	}


	/**
	 *Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 *weryfikację tekstu nagłówka, i następnie przejście do podstrony Notifications, następnie
	 przesłanie powiadomienia i weryfikację, czy powiadomienie o przesłanym powiadomieniu miało callback.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Notification test")
	@Description("Test sprawdza działanie powiadomień systemowych.")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Kamil")
	@Test(groups = {"devTools"}, priority = 1)
	public void notificationPageTest() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Notifications");
		NotificationPage notificationPage = new NotificationPage(DriverFactory.getDriver(), DriverFactory.getWait());
		notificationPage.verifyAbstractPage();
		notificationPage.sendMeNotification();
		notificationPage.createAndSendNotification("Powiadomienie", "To działające powiadomienie uruchomione poprzez JS");
	}

	/**
	 *Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 *weryfikację tekstu nagłówka, i następnie przejście do podstrony Get user media, i uruchomienie fejkowej
	 kamery i weryfikacja widoczności informacji o urządzeniu.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Media test")
	@Description("Test sprawdza działanie mediów (kamera, mikrofon).")
	@Severity(SeverityLevel.NORMAL)
	@Owner("Kamil")
	@Test(groups = {"devTools"}, priority = 2)
	public void userMediaPageTest() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Get user media");
		UserMediaPage userMediaPage = new UserMediaPage(DriverFactory.getDriver(), DriverFactory.getWait());
		userMediaPage.verifyAbstractPage();
		userMediaPage.runUserMedia();
		log.info("W teście po uruchomieniu mediów przeglądarka używa : {}", userMediaPage.getVideoDeviceInfo());
		Screenshot.takeScreenshot();
		log.info("Screenshot został wykonany");
	}

	/**
	 *Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 *weryfikację tekstu nagłówka, i następnie przejście do podstrony Multilanguage, i sprawdzenie widoczności
	 tekstu w paragrafach,
	 *który to tekst powinien być przetłumaczony na hiszpański.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Multilagnguage")
	@Description("Test sprawdza działanie mechanizmu tłumaczenia strony.")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Kamil")
	@Test()
	public void multiLanguageTest() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Multilanguage");
		MultilanguagePage multilanguagePage = new MultilanguagePage(DriverFactory.getDriver(), DriverFactory.getWait());
		multilanguagePage.verifyAbstractPage();
		log.info("Paragrafy posiadają następujące wartości: {}", multilanguagePage.getParagraphsInfo());
	}

	/**
	 *Test ma na celu uruchomienie przeglądarki i uruchomienie nasłuchiwania logów, przejście do głównej strony,
	 *weryfikację tekstu nagłówka, i następnie przejście do podstrony Console logs, i wydrukowanie logówz konsoli.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Console test")
	@Description("Test sprawdza działanie konsoli.")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Kamil")
	@Test(groups = {"devTools, interface"}, priority = 1)
	public void consoleLogsTest() throws ExecutionException, InterruptedException, TimeoutException {
		ConsoleLogsPage consoleLogsPage = new ConsoleLogsPage(DriverFactory.getDriver(), DriverFactory.getWait());
		consoleLogsPage.startListening();
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Console logs");
		consoleLogsPage.verifyAbstractPage();
		consoleLogsPage.getConsoleLogs();

	}


	@Epic("Boni Garcia WebPageTest")
	@Story("Login form test")
	@Description("Test sprawdza działanie mechanizmu logowania.")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Kamil")
	@Test(groups = {"interface, business"}, priority = 0)
	public void loginFormTest() {
		/**
		 *Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
		 *weryfikację tekstu nagłówka, i następnie przejście do podstrony Login form, a następnie zalogowanie się i
		 *weryfikację widoczności komunikatu o poprawnym zalogowaniu.
		 */

		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Login form");
		LoginFormPage loginFormPage = new LoginFormPage(DriverFactory.getDriver(), DriverFactory.getWait());
		loginFormPage.verifyAbstractPage();
		loginFormPage.logIn();
		loginFormPage.clickSubmitBtn();
		loginFormPage.verifySuccessLogin();

	}
	/**
	 *Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 *weryfikację tekstu nagłówka, i następnie przejście do podstrony Login form, a następnie zalogowanie się i
	 *weryfikację widoczności komunikatu o poprawnym zalogowaniu.Wszystko to z uwzględnieniem wolniejszego działania
	 formularza.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Slow login form test")
	@Description("Test sprawdza działanie mechanizmu logowania przy obciążeniu strony.")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Kamil")
	@Test(groups = {"interface, business"}, priority = 0)
	public void slowLoginFormTest() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Slow login");
		LoginFormPage loginFormPage = new LoginFormPage(DriverFactory.getDriver(), DriverFactory.getWait());
		loginFormPage.verifyAbstractPage();
		loginFormPage.logIn();
		loginFormPage.clickSubmitBtn();
		loginFormPage.verifySuccessLogin();
	}

	/**
	 *Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 *weryfikację tekstu nagłówka, i następnie przejście do podstrony Random calculator, a następnie wykonanie
	 obliczeń w pętli.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Random calculator test")
	@Description("Test sprawdza działanie kalkulatora generującego losowe wyniki.")
	@Severity(SeverityLevel.NORMAL)
	@Owner("Kamil")
	@Test(groups = {"interface, business"}, priority = 1)
	public void randomCalculator() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Random calculator");
		RandomCalculatorPage randomCalculator = new RandomCalculatorPage(DriverFactory.getDriver(), DriverFactory.getWait());
		randomCalculator.verifyAbstractPage();
		randomCalculator.setCorrectTimesToRun("20");
		for (int i = 1; i < 10; i++) {
			randomCalculator.setPercentOfCorrectResults("15");
			String result = randomCalculator.calculate("2+2=");
			log.info("Wynik działania uruchomienia numer {} to: {}", i, result);
			randomCalculator.setPercentOfCorrectResults("99");
			result = randomCalculator.calculate("2+2=");
			log.info("Wynik działania uruchomienia numer {} to: {}", i, result);
		}
	}


	/**
	 *Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 *weryfikację tekstu nagłówka, i następnie przejście do podstrony Download files,
	 *i pobranie wybranego pliku poprzez użycie przycisku na stronie.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Download file test")
	@Description("Test sprawdza działanie mechanizmu pobierania plików.")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Kamil")
	@Test(groups = {"interface, business"}, priority = 0)
	public void downloadFileTest() throws IOException {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Download files");
		FileDownloadPage fileDownloadPage = new FileDownloadPage(DriverFactory.getDriver(), DriverFactory.getWait());
		fileDownloadPage.verifyAbstractPage();
		try {
			fileDownloadPage.downloadFile();
		} catch (InterruptedException | IOException e) {
			throw new FileNotFoundException(e.getMessage());
		}
		FileDownloadUtils.clearDownloadFolderFromFiles();
	}


	/**
	 *Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 *weryfikację tekstu nagłówka, i następnie przejście do podstrony A/B Testing, i weryfikację, który
	 wariant strony uruchomił się w tym teście.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Double variant test")
	@Description("Test sprawdza działanie mechanizmu losowego generowania treści na stronie.")
	@Severity(SeverityLevel.TRIVIAL)
	@Owner("Kamil")
	@Test(groups = {"interface, business"}, priority = 5)
	public void testDoubleVariant() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("A/B Testing");
		ABTestingPage abTestingPage = new ABTestingPage(DriverFactory.getDriver(), DriverFactory.getWait());
		abTestingPage.verifyAbstractPage();
		abTestingPage.verifyTypeOfVariation();
	}

	/***
	 * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
	 * weryfikację tekstu nagłówka, i następnie przejście do podstrony Data types, wypełnienie formularza i weryfikację,
	 * czy wszystkie pola posiadają poprawne dane po przesłaniu formularza.
	 */
	@Epic("Boni Garcia WebPageTest")
	@Story("Data types test")
	@Description("Test sprawdza działanie mechanizmu wypełniania formularza losowymi danymi.")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Kamil")
	@Test(groups = {"business"}, priority = 1)
	public void dataTypesTest() {
		MainPage mainPage = new MainPage(DriverFactory.getDriver(), DriverFactory.getWait());
		mainPage.openMainPage();
		mainPage.goToSubPage("Data types");
		DataTypesPage dataTypesPage = new DataTypesPage(DriverFactory.getDriver(), DriverFactory.getWait());
		dataTypesPage.verifyAbstractPage();
		dataTypesPage.insertDataToForm();
		dataTypesPage.submitForm();
		dataTypesPage.verifySuccessForm();
	}
}
