package POM.WebTest.BoniGarcia.Tests;

import POM.WebTest.BoniGarcia.Pages.*;
import POM.WebTest.BoniGarcia.Utils.DropdownOptions;
import POM.WebTest.BoniGarcia.Utils.PointForCanvas;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         */
        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("IFrames");
        DialogBoxesPage dialogBoxesPage = new DialogBoxesPage(driver, wait, log);
        dialogBoxesPage.verifyAbstractPage();





        dialogBoxesPage.launchAlert();
        String alertString = dialogBoxesPage.getTextFromAlert();
        log.info("Alert posiada tekst: {}", alertString);
        dialogBoxesPage.launchConfirm();
        dialogBoxesPage.getTextFromConfirm("ok");
        assertThat(dialogBoxesPage.confirmTextParagraph.getText()).isEqualTo("You chose: true");
        String promptText = "To jest prompt :)";
        dialogBoxesPage.launchPrompt();
        dialogBoxesPage.setTextToPromptAlert(promptText);
        assertThat(dialogBoxesPage.promptTextParagraph.getText()).isEqualTo("You typed: " + promptText);
        dialogBoxesPage.handleModalWindow("save");
        assertThat(dialogBoxesPage.modalTextParagraph.getText()).isEqualTo("You chose: Save changes");
    }

    @Test()
    public void webStoragePageTests(){
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Web storage");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/web-storage.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);


        webStoragePage.clearLocalStorage();
        webStoragePage.clearSessionStorage();
        webStoragePage.inputValueToLocalStorage("Klucz testowy sesji lokalnej 1", "Wartość testowa");
        webStoragePage.inputValueToLocalStorage("Klucz testowy sesji lokalnej 2", "Wartość testowa");
        webStoragePage.inputValueToSessionStorage("Klucz testowy pamięci sesji 1", "Wartość testowa sesji");
        webStoragePage.inputValueToSessionStorage("Klucz testowy pamięci sesji 2", "Wartość testowa sesji");
        webStoragePage.clickLocalStorageBtn();
        webStoragePage.clickLocalSessionBtn();
        log.info("Local storage posiada wartości: {}", webStoragePage.localStorageParagraph.getText());
        log.info("Session storage posiada wartości: {}", webStoragePage.localSessionParagraph.getText());
    }


    @Test()
    public void geolocationTest(){
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Geolocation");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/geolocation.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);


        geolocationPage.setCoordinates(4.323, 4.53);
        geolocationPage.clickGeolocationBtn();
        log.info("Koordynaty to: {}", geolocationPage.returnCoordinates());
        assertThat(geolocationPage.returnCoordinates().contains("4.323"));
    }

    @Test()
    public void notificationPageTest(){
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Notifications");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/notifications.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);

        notificationPage.sendMeNotification();
        notificationPage.createAndSendNotification("Powiadomienie", "To działające powiadomienie uruchomione poprzez JS");
    }

    @Test()
    public void userMediaPageTest(){
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Get user media");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/get-user-media.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);

        userMediaPage.runUserMedia();
        String videoInfo = userMediaPage.getVideoDeviceInfo();
        log.info("W teście po uruchomieniu mediów przeglądarka używa : {}", videoInfo);
        try {
            userMediaPage.takeScreenshot("userMediaPageTest");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test()
    public void multiLanguageTest(){
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Multilanguage");
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/multilanguage.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);

        String paragraphsEs = multilanguagePage.getParagraphsInfo();
        log.info("Paragrafy posiadają następujące wartości: {}", paragraphsEs);
    }

    @Test()
    public void consoleLogsTest() throws ExecutionException, InterruptedException, TimeoutException {
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        consoleLogsPage.startListening();
        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Console logs");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/console-logs.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);

        consoleLogsPage.getConsoleLogs();

    }

    @Test()
    public void loginFormTest() {
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        consoleLogsPage.startListening();
        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Login form");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/login-form.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);

        loginFormPage.logIn();
        loginFormPage.clickSubmitBtn();
        loginFormPage.verifySuccessLogin();

    }

    @Test()
    public void slowLoginFormTest() {
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        consoleLogsPage.startListening();
        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Slow login");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/login-slow.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);

        loginFormPage.logIn();
        loginFormPage.clickSubmitBtn();
        loginFormPage.verifySuccessLogin();

    }


    @Test()
    public void slowCalculator() {
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        consoleLogsPage.startListening();
        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Random calculator");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/random-calculator.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);


        randomCalculatorPage.setCorrectTimesToRun("20");
        for (int i = 1; i < 10; i++) {
            randomCalculatorPage.setPercentOfCorrectResults("15");
            String result = randomCalculatorPage.calculate("2+2=");
            log.info("Wynik działania uruchomienia numer {} to: {}", i, result);

            randomCalculatorPage.setPercentOfCorrectResults("99");
            result = randomCalculatorPage.calculate("2+2=");
            log.info("Wynik działania uruchomienia numer {} to: {}", i, result);
        }
    }

        @Test()
        public void downloadFileTest() throws FileNotFoundException {
            */
/***
             * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
             * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
             *//*

            consoleLogsPage.startListening();
            driver.get(mainPage.boniGarciaMainURL);
            wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
            mainPage.goToSubPage("Download files");
            wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/download.html")) {
                log.info("Adres URL jest poprawny.");
            } else {
                log.error("Niepoprawny adres URL: " + currentUrl);
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
            assertThat(driver.findElement(ap.img)
                    .isDisplayed()).isTrue();
            assertThat(driver.findElement(mainPage.copySpan)
                    .getText()).contains(ap.copyrights);

            try {
                fileDownloadPage.downloadFile(1);
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException(e.getMessage());
            }

        }


    @Test()
    public void ABtesting() {
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        consoleLogsPage.startListening();
        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("A/B Testing");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/ab-testing.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);

        abTestingPage.verifyTypeOfVariation();

    }


    @Test()
    public void dataTypesTest() {
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         */

    /*    consoleLogsPage.startListening();
        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Data types");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/data-types.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);

        dataTypesPage.insertDataToForm();
        dataTypesPage.submitForm();
        dataTypesPage.verifySuccessForm();*/
    }

}




