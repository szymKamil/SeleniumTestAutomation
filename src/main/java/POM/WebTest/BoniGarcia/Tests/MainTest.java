package POM.WebTest.BoniGarcia.Tests;

import Base.Utils.Screenshot;
import POM.WebTest.BoniGarcia.Pages.*;
import POM.WebTest.BoniGarcia.Utils.DropdownOptions;
import POM.WebTest.BoniGarcia.Utils.PointForCanvas;
import java.;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.*;
import org.testng.annotations.Optional;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;


public class MainTest extends BaseTest {


    @Test(priority = 1)
    public void mainPageTestElementsVerification() {
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz występowania głównych elementów na stronie.
         */
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.verifyHomePageContent();
    }



    @Parameters("path")
    @Test(priority = 1)
    public void webFormTest(@Optional("D:\\Programowanie\\Nauka\\SeleniumTestAutomation\\SeleniumTestAutomation\\src\\main\\resources\\f-vat_2011.pdf")String path) {
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, następnie przejście do podstrony Web form,  na której przetestowane zostanie możliwość wypełnienia, weryfikacji i przesłania formularza oraz upewnienie się,
         * że formularz został poprawnie przesłany poprzez weryfikację wyświetlania odpowiedniego komunikatu o sukcesie.
         */
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Web form");
        WebForm webForm = new WebForm(driver, wait, log);
        webForm.verifyAbstractPage();
        webForm.fillTextInput();
        webForm.fillPasswordInput();
        webForm.fillTextAreaInput();
        webForm.selectElementOnDropdownList("Two");
        webForm.verifyDisabledFields();
        webForm.selectElementOnDataList(DropdownOptions.OPTION_B);
        webForm.uploadFile(path);
        webForm.checkboxSelector();
        webForm.radioSelector(2);
        webForm.colorPicker(new Color(65, 45, 34, 2).asHex());
        webForm.dateSetter(LocalDate.now());
        webForm.changeRange();
        webForm.submitForm();
    }



    @Test(priority = 3, dependsOnMethods ={"mainPageTestElementsVerification"})
    public void navigationPageTest(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, następnie przejście do podstrony Navigation, a następnie weryfikację działania przycisków i weryfikację
         * zmieniającej się treści w paragrafach.
         */
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Navigation");
        NavigationPage navigationPage = new NavigationPage(driver, wait, log);
        navigationPage.verifyAbstractPage();
        navigationPage.verifyBtns();
    }


    @Test()
    public void dropdownMenuTest(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, następnie przejście do podstrony Dropdown menu, i użycie wybranych dropdownów razem z kliknięciem odpowiednich przycisków w menu.
         */
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Dropdown menu");
        DropdownMenuPage dropdownMenu = new DropdownMenuPage(driver, wait, log);
        dropdownMenu.verifyAbstractPage();
        dropdownMenu.openDropOneAndPick(1);
        dropdownMenu.openDropTwoAndPick(2);
        dropdownMenu.openDropThreeAndPick(3);

    }

    @Test()
    public void mouseOverTest() {
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, następnie przejście do podstrony Mouse over i weryfikacje podpisów pod rysunkami,
         * które widoczne są dopiero po umieszczeniu kursora na obrazkach.
         */
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Mouse over");
        MouseOverPage mouseOverPage = new MouseOverPage(driver, wait, log);
        mouseOverPage.verifyAbstractPage();
        mouseOverPage.hoverOverImg(2);
        mouseOverPage.choseAndWaitFormElementToBeVisible("Award");
        mouseOverPage.verifyImgCaptions();
    }

        @Test()
        public void dragAndDropTest(){
            /***
             * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
             * weryfikację tekstu nagłówka, i następnie przejście do podstrony Drag and drop, na której test przeniesie element do miejsca docelowego.
             */
            MainPage mainPage = new MainPage(driver, wait, log);
            mainPage.openMainPage();
            mainPage.goToSubPage("Drag and drop");
            DragAndDropPage dragAndDrop = new DragAndDropPage(driver, wait, log);
            dragAndDrop.verifyAbstractPage();
            dragAndDrop.getElementCoords();
            dragAndDrop.getTargetCoords();
            dragAndDrop.dragPanelTo(dragAndDrop.getTargetCoords());
            dragAndDrop.getElementCoords();
            dragAndDrop.getTargetCoords();

        }

    @Test()
    public void canvasTest(){
         /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, i następnie przejście do podstrony Draw in canvas, na której zostanie użyte płótno do narysowania przykłądowego kształtu.
         */
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Draw in canvas");
        CanvasPage canvasPage = new CanvasPage(driver, wait, log);
        canvasPage.verifyAbstractPage();
        canvasPage.paintInCanvas();
        canvasPage.paintInCanvas(new PointForCanvas(50, 20), new PointForCanvas(30, 10), new PointForCanvas(-20, -15));
    }


    @Test()
    public void loadingImages(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, i następnie przejście do podstrony Loading images a następnie weryfikację, czy obrazki zostaną wyświetlone.
         */
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Loading images");
        LoadingImagesPage loadingImagesPage = new LoadingImagesPage(driver, wait, log);
        loadingImagesPage.verifyAbstractPage();
        loadingImagesPage.waitForImagesToLoad();
        loadingImagesPage.confirmElementsVisibility();


    }


    @Test()
    public void slowCalculatorTest(){
         /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, i następnie przejście do podstrony Slow calculator, i użycie kalkulatora do wykonania obliczeń.
         */

        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Slow calculator");
        SlowCalculator slowCalculator = new SlowCalculator(driver, wait, log);
        slowCalculator.verifyAbstractPage();
        slowCalculator.verifyAbstractPage();
        slowCalculator.setCalcDelay(3);
        slowCalculator.useCalculator("2+3=");
        slowCalculator.getResultOfCalc();


    }


    @Test()
    public void longPageTest(){
         /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, i następnie przejście do podstrony Long page, i przescrollowanie jej na sam koniec.
         */

        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Long page");
        LongPage longPage = new LongPage(driver, wait, log);
        longPage.verifyAbstractPage();
        longPage.scrollToLastParagraph();
        longPage.getTextFromParagraph(3);
        longPage.printAllParagraphs();

    }

    @Test()
    public void infiniteScrollTest(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, i następnie przejście do podstrony Infinite scroll, a następnie przescrollowanie strony.
         */
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Infinite scroll");
        InfiniteScrollPage infiniteScroll = new InfiniteScrollPage(driver, wait, log);
        infiniteScroll.verifyAbstractPage();
        log.info("Początkowa wysokość strony to: {}", infiniteScroll.getPageHeight());
        infiniteScroll.scrollXTimes();
        log.info("Końcowa wysokość strony to: {}", infiniteScroll.getPageHeight());
    }

    @Test()
    public void shadowRootTest(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, i następnie przejście do podstrony Shadow DOM i odczytanie wartości z elementu ukrytego w shadow DOM.
         */
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Shadow DOM");
        ShadowDomPage shadowDomPage = new ShadowDomPage(driver, wait, log);
        shadowDomPage.verifyAbstractPage();
        shadowDomPage.getShadowRootContent();
    }

    @Test()
    public void cookiesPageTest(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, i następnie przejście do podstrony Cookies, następnie pobranie domyślnych wartości ciasteczek,
         * modyfikacja ich i weryfikacja, czy ciasteczka się zmieniły.
         */

        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Cookies");
        CookiesPage cookiesPage = new CookiesPage(driver, wait, log);
        cookiesPage.verifyAbstractPage();
        cookiesPage.refreshCookiesBtn();
        cookiesPage.getCookiesText();
        cookiesPage.deleteAllCookies();
        cookiesPage.addCookie("Username", "RobertZamojski");
        cookiesPage.addCookie("data", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
        cookiesPage.refreshCookiesBtn();
        cookiesPage.getCookiesText();
    }


    @Test()
    public void framesPageTest(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, i następnie przejście do podstrony Frames, a następnie weryfikację widoczności elementów ukrytych w
         * różnych sekcjach i sprawdzenie możliwości przełączania się pomiędzy sekcjami strony.
         */

        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Frames");
        FramesPage framesPage = new FramesPage(driver, wait, log);
        framesPage.switchToFrame("header");
        framesPage.verifyMainHeader();
        framesPage.verifyImage();
        framesPage.switchToFrame("footer");
        framesPage.verifyCopyrights();
        framesPage.switchToFrame("body");
        framesPage.verifyVisibilityOfParagraphs();

    }

    @Test()
    public void iFramesPageTest(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, i następnie przejście do podstrony IFrames, przescrollowanie i weryfikację tekstu w ostatnim paragrafie.
         */
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("IFrames");
        IFramePage iFramePage = new IFramePage(driver, wait, log);
        iFramePage.verifyAbstractPage();
        iFramePage.switchToiFrame();
        iFramePage.scrollPage();
        String testString = "Magnis feugiat natoque proin commodo laoreet mauris, odio ligula sagittis montes dapibus fames ultricies, interdum ridiculus volutpat aenean pulvinar. " +
                "Fames curabitur himenaeos nec porta lectus tempus, conubia purus nam lacus rhoncus primis, class fusce fermentum velit tortor. " +
                "Non consequat fringilla mauris mus tortor commodo cum, quis ultrices lobortis curabitur ad pulvinar massa imperdiet, primis quisque nisi ultricies purus lacus.";
        assertThat(iFramePage.getTextFromLastParagraph()).isEqualTo(testString);
    }


    @Test()
    public void alertPageTest(){
         /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, i następnie przejście do podstrony Dialog boxes, następnie uruchomienie każdego z aletrów
          * i weryfikację poprawności ich wyświetlania i działania, w kontekście przesłanego tekstu.
         */
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Dialog boxes");
        DialogBoxesPage dialogBoxesPage = new DialogBoxesPage(driver, wait, log);
        dialogBoxesPage.verifyAbstractPage();
        dialogBoxesPage.launchAlert();
        String alertString = dialogBoxesPage.getTextFromAlert();
        log.info("Alert posiada tekst: {}", alertString);
        dialogBoxesPage.launchConfirm();
        String confirmText = dialogBoxesPage.getTextFromConfirm("ok");
        assertThat(confirmText).isEqualTo("You chose: true");
        String promptText = "To jest prompt :)";
        dialogBoxesPage.launchPrompt();
        dialogBoxesPage.setTextToPromptAlert(promptText);
        assertThat(dialogBoxesPage.getTextFromPrompt()).isEqualTo("You typed: " + promptText);
        dialogBoxesPage.handleModalWindow("save");
        assertThat(dialogBoxesPage.getTextFromModal()).isEqualTo("You chose: Save changes");
    }

    @Test()
    public void webStoragePageTests(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, i następnie przejście do podstrony Web storage, następnie wyczyszczenie lokalnej pamięci przeglądarki i dodanie nowych kluczy,
         * a na końcu weryfikacja ich widoczności.
         */
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Web storage");
        WebStoragePage webStoragePage = new WebStoragePage(driver, wait, log);
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


    @Test()
    public void geolocationTest(){
         /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, i następnie przejście do podstrony Geolocation, następnie zmianę ustawień przeglądarki i zmianę informacji o geolokacji, miejscu przeglądarki,
          * i weryfikację widoczności tych zmian.
         */
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Geolocation");
        GeolocationPage geolocationPage = new GeolocationPage(driver, wait, log);
        geolocationPage.verifyAbstractPage();

        geolocationPage.setCoordinates(4.323, 4.53);
        geolocationPage.clickGeolocationBtn();
        log.info("Koordynaty to: {}", geolocationPage.returnCoordinates());
        assertThat(geolocationPage.returnCoordinates().contains("4.323"));
    }

    @Test()
    public void notificationPageTest(){
         /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, i następnie przejście do podstrony Notifications, następnie przesłanie powiadomienia i weryfikację, czy powiadomienie o przesłanym powiadomieniu miało callback.
         */
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Notifications");
        NotificationPage notificationPage = new NotificationPage(driver, wait, log);
        notificationPage.verifyAbstractPage();
        notificationPage.sendMeNotification();
        notificationPage.createAndSendNotification("Powiadomienie", "To działające powiadomienie uruchomione poprzez JS");
    }

    @Test()
    public void userMediaPageTest(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, i następnie przejście do podstrony Get user media, i uruchomienie fejkowej kamery i weryfikacja widoczności informacji o urządzeniu.
         */
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Get user media");
        UserMediaPage userMediaPage = new UserMediaPage(driver, wait, log);
        userMediaPage.verifyAbstractPage();
        userMediaPage.runUserMedia();
        log.info("W teście po uruchomieniu mediów przeglądarka używa : {}", userMediaPage.getVIDEO_DEVICE_INFO());
        Screenshot.takeScreenshot(driver);
        log.info("Screenshot został wykonany");
    }

    @Test()
    public void multiLanguageTest(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, i następnie przejście do podstrony Multilanguage, i sprawdzenie widoczności tekstu w paragrafach,
         * który to tekst powinien być przetłumaczony na hiszpański.
         */
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Multilanguage");
        MultilanguagePage multilanguagePage = new MultilanguagePage(driver, wait, log);
        multilanguagePage.verifyAbstractPage();
        log.info("Paragrafy posiadają następujące wartości: {}", multilanguagePage.getParagraphsInfo());
    }


    @Test()
    public void consoleLogsTest() throws ExecutionException, InterruptedException, TimeoutException {
        /***
         * Test ma na celu uruchomienie przeglądarki i uruchomienie nasłuchiwania logów, przejście do głównej strony,
         * weryfikację tekstu nagłówka, i następnie przejście do podstrony Console logs, i wydrukowanie logówz konsoli.
         */
        ConsoleLogsPage consoleLogsPage = new ConsoleLogsPage(driver, wait, log);
        consoleLogsPage.startListening();
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Console logs");
        consoleLogsPage.verifyAbstractPage();
        consoleLogsPage.getConsoleLogs();

    }

    @Test()
    public void loginFormTest() {
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, i następnie przejście do podstrony Login form, a następnie zalogowanie się i
         * weryfikację widoczności komunikatu o poprawnym zalogowaniu.
         */
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Login form");
        LoginFormPage loginFormPage = new LoginFormPage(driver, wait, log);
        loginFormPage.verifyAbstractPage();

        loginFormPage.logIn();
        loginFormPage.clickSubmitBtn();
        loginFormPage.verifySuccessLogin();

    }

    @Test()
    public void slowLoginFormTest() {
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, i następnie przejście do podstrony Login form, a następnie zalogowanie się i
         * weryfikację widoczności komunikatu o poprawnym zalogowaniu. Wszystko to z uwzględnieniem wolniejszego działania formularza.
         */

        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Slow login");
        LoginFormPage loginFormPage = new LoginFormPage(driver, wait, log);
        loginFormPage.verifyAbstractPage();
        loginFormPage.logIn();
        loginFormPage.clickSubmitBtn();
        loginFormPage.verifySuccessLogin();
    }


    @Test()
    public void randomCalculator() {
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, i następnie przejście do podstrony Random calculator, a następnie wykonanie obliczeń w pętli.
         */
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Random calculator");
        RandomCalculatorPage randomCalculator = new RandomCalculatorPage(driver, wait, log);
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

        @Test()
        public void downloadFileTest() throws FileNotFoundException {
            /***
             * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
             * weryfikację tekstu nagłówka, i następnie przejście do podstrony Download files,
             * i pobranie wybranego pliku poprzez użycie przycisku na stronie.
             */
            MainPage mainPage = new MainPage(driver, wait, log);
            mainPage.openMainPage();
            mainPage.goToSubPage("Download files");
            FileDownloadPage fileDownloadPage = new FileDownloadPage(driver, wait, log);
            fileDownloadPage.verifyAbstractPage();
            try {
                fileDownloadPage.downloadFile(1);
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException(e.getMessage());
            }

        }


    @Test()
    public void ABtesting() {
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację tekstu nagłówka, i następnie przejście do podstrony A/B Testing, i weryfikację, który wariant strony uruchomił się w tym teście.
         */
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("A/B Testing");
        ABTestingPage abTestingPage = new ABTestingPage(driver, wait, log);
        abTestingPage.verifyAbstractPage();
        abTestingPage.verifyTypeOfVariation();

    }


    @Test()
    public void dataTypesTest() {
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         */

        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Data types");
        DataTypesPage dataTypesPage = new DataTypesPage(driver, wait, log);
        dataTypesPage.verifyAbstractPage();
        dataTypesPage.insertDataToForm();
        dataTypesPage.submitForm();
        dataTypesPage.verifySuccessForm();*/
    }

}




