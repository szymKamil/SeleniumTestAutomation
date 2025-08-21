package POM.WebTest.BoniGarcia.Tests;

import POM.WebTest.BoniGarcia.Pages.MainPage;
import POM.WebTest.BoniGarcia.Pages.WebForm;
import POM.WebTest.BoniGarcia.Utils.DropdownOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.*;

import java.time.LocalDate;

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
         * weryfikację adresu URL oraz tekstu nagłówka, a następnie wypełnienie, weryfikację i przesłanie formularza oraz upewnienie się,
         * że formularz został poprawnie przesłany.
         */

        MainPage mainPage = new MainPage(driver, wait, log);
        mainPage.openMainPage();
        mainPage.goToSubPage("Web form");
        ap.verifyAbstractPage();
        WebForm webForm = new WebForm(driver, wait, log);
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

}
/*
    @Test(priority = 2, dependsOnMethods ={"mainPageTestElementsVerification"})
    public void navigationPageTest(){
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, a następnie wypełnienie i przesłanie formularza.
         *//*

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Navigation");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/navigation1.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);
        StringBuilder loremIpsum = new StringBuilder(navigationPage.leadParagraph.getText());
        navigationPage.activeBtnInfo();
        log.info("Pierwszy paragraf brzmi: {}", loremIpsum);
        navigationPage.btnNext();
        navigationPage.activeBtnInfo();
        loremIpsum = new StringBuilder(navigationPage.leadParagraph.getText());
        log.info("Drugi paragraf brzmi: {}", loremIpsum);
        navigationPage.btnNext();
        navigationPage.activeBtnInfo();
        loremIpsum = new StringBuilder(navigationPage.leadParagraph.getText());
        log.info("Trzeci paragraf brzmi: {}", loremIpsum);
        navigationPage.btnPrev();
        navigationPage.activeBtnInfo();
        navigationPage.btnPrev();
        navigationPage.activeBtnInfo();
    }


    @Test()
    public void dropdownMenuTest(){
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i użycie wybranych dropdownów i kliknięcie w odpowiednie linki.
         *//*

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Dropdown menu");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/dropdown-menu.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);
        dropdownMenu.openDropdownOne();
        dropdownMenu.pickDropdownElement( 1);
        dropdownMenu.openDropdownTwo();
        dropdownMenu.pickDropdownElement( 2);
        dropdownMenu.openDropdownThree();
        dropdownMenu.pickDropdownElement( 3);
    }

    @Test()
    public void mouseOverTest() {
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .
         *//*

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Mouse over");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/mouse-over.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);

        String[] imgCaptions = {"Compass", "Calendar", "Award", "Landscape"};

        for (int i = 0; i < imgCaptions.length; i++) {
            mouseOver.hoverOverImg(i);
            String imgCaption = mouseOver.getCaptionElementByIndex(i)
                    .getText();
            log.info("Element numer {} ma podpis: {}", (i + 1), imgCaption);
        }
    }

        @Test()
        public void dragAndDropTest(){
            */
/***
             * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
             * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
             *//*

            driver.get(mainPage.boniGarciaMainURL);
            wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
            mainPage.goToSubPage("Drag and drop");
            wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/drag-and-drop.html")) {
                log.info("Adres URL jest poprawny.");
            } else {
                log.error("Niepoprawny adres URL: " + currentUrl);
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
            assertThat(driver.findElement(ap.img)
                    .isDisplayed()).isTrue();
            assertThat(driver.findElement(mainPage.copySpan)
                    .getText()).contains(ap.copyrights);
            log.info("Koordynaty panelu to: {} i {} ", dragAndDrop.draggablePanel.getLocation().x, dragAndDrop.draggablePanel.getLocation().y);
            log.info("Koordynaty miejsca docelowego to: {} i {} ", dragAndDrop.target.getLocation().x, dragAndDrop.target.getLocation().y);
            dragAndDrop.dragPanelTo(dragAndDrop.target);
            log.info("Koordynaty panelu po przeniesieniu to: {} i {} ", dragAndDrop.draggablePanel.getLocation().x, dragAndDrop.draggablePanel.getLocation().y);
            dragAndDrop.dragPanelTo();
            log.info("Koordynaty panelu po kolejnym przeniesieniu to: {} i {} ", dragAndDrop.draggablePanel.getLocation().x, dragAndDrop.draggablePanel.getLocation().y);

        }

    @Test()
    public void canvas(){
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Draw in canvas");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/draw-in-canvas.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);
        canvasPage.paintInCanvas();
    }


    @Test()
    public void loadingImages(){
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Loading images");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);
        loadingImagesPage.waitForImagesToLoad();
        wait.until(ExpectedConditions.textToBePresentInElement(loadingImagesPage.paragraphText, "Done!"));
        log.info("Wszystkie elementy są widoczne i poprawnie zostały załadowane.");


    }


    @Test()
    public void slowCalculatorTest(){
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Slow calculator");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/slow-calculator.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);
        assertThat(slowCalculator.delayInput.isDisplayed()).isTrue();
        slowCalculator.setCalcDelay(3);
        String delayModified = slowCalculator.delayInput.getAttribute("value");
        log.info("Kalkulator ma delay ustawiony na {}", delayModified);
        slowCalculator.useCalculator("2+3=");
        String result = slowCalculator.getResultOfCalc();
        log.info("Wynik działania to: {}", result);

    }


    @Test()
    public void longPageTest(){
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Long page");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/long-page.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);
        String paragraph = longPage.getTextFromParagraph(3);
        log.info("Paragraf {} ma tekst {}", 4, paragraph);
        longPage.scrollToLastParagraph();
        try {
            Thread.sleep(Duration.ofSeconds(4));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        longPage.printAllParagraphs();

    }

    @Test()
    public void infiniteScroll(){
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Infinite scroll");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/infinite-scroll.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);
        log.info("Poczatkowa wysokosc strony to: {}", infiniteScroll.getPageHeight());
        infiniteScroll.scrollXTimes();
        log.info("Końcowa wysokość strony to: {}", infiniteScroll.getPageHeight());
    }

    @Test()
    public void shadowRootTest(){
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Shadow DOM");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/shadow-dom.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);

        String shadowDOMtext = shadowDomPage.getShadowRootContent();
        log.info("ShadowDOM posiada tekst: {}", shadowDOMtext);
    }

    @Test()
    public void cookiesPageTest(){
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Cookies");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/cookies.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);

        wait.until(ExpectedConditions.elementToBeClickable(cookiesPage.refreshCookiesBtn));
        cookiesPage.refreshCookiesBtn.click();
        String cookies = cookiesPage.cookiesParagraph.getText();
        log.info("Ciasteczka są następujące: {}", cookies);
        cookiesPage.deleteAllCookies();
        cookiesPage.addCookie("Username", "RobertZamojski");
        cookiesPage.addCookie("data", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE).toString());
        cookiesPage.refreshCookiesBtn.click();
        String cookiesModified = cookiesPage.cookiesParagraph.getText();
        log.info("Ciasteczka są następujące: {}", cookiesModified);

    }


    @Test()
    public void framesPageTest(){
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Frames");
        framesPage.switchToFrame(framesPage.frameHeader);

        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/frames.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        framesPage.switchToFrame(framesPage.frameFooter);
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);

        framesPage.switchToFrame(framesPage.frameBody);
        int paragraphsSize = driver.findElements(By.cssSelector("p")).size();
        log.info("W teście po przełączeniu się na ramkę widocznych jest {} paragrafów", paragraphsSize);

    }

    @Test()
    public void iFramesPageTest(){
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("IFrames");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/iframes.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);

        iFramePage.switchToiFrame();
        iFramePage.scrollPage();
        WebElement lastP = driver.findElement(By.xpath("//p[last()]"));
        assertThat(lastP.getText()).contains("Magnis feugiat natoque proin commodo laoreet mauris, " +
                "odio ligula sagittis montes dapibus fames ultricies, interdum ridiculus volutpat aenean pulvinar.");
    }


    @Test()
    public void alertPageTest(){
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Dialog boxes");
        wait.until(ExpectedConditions.visibilityOf(navigationPage.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);

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
        */
/***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         *//*

        consoleLogsPage.startListening();
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
        dataTypesPage.verifySuccessForm();
    }

}



*/
