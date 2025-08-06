package POM.WebTest.BoniGarcia.Tests;




import POM.WebTest.BoniGarcia.Utils.DropdownOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v137.domstorage.DOMStorage;
import org.openqa.selenium.devtools.v137.domstorage.model.StorageId;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.*;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;


public class MainTest extends BaseTest {



    @Test
    public void mainPageTestElementsVerification(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka.
         */
        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        assert currentUrl != null;
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        assertThat(driver.findElement(ap.img).isDisplayed()).isTrue();
        assertThat(driver.findElement(ap.mainHeader).getText()).isEqualTo(ap.pageTitle);
        assertThat(driver.findElements(mainPage.containers).size()).isEqualTo(6);
        assertThat(driver.findElement(mainPage.lead).isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.lead).getText()).isEqualTo(ap.leadText);
        assertThat(driver.findElement(mainPage.copySpan).getText()).contains(ap.copyrights);
    }

    @Parameters("path")
    @Test(priority = 2, dependsOnMethods ={"mainPageTestElementsVerification"})
    public void webFormTest(@Optional("D:\\Programowanie\\Nauka\\SeleniumTestAutomation\\SeleniumTestAutomation\\src\\main\\resources\\f-vat_2011.pdf") String path){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, a następnie wypełnienie i przesłanie formularza.
         */
        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Web form");
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/web-form.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: {}", currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);
        assertThat(driver.findElement(By.linkText("Return to index"))
                .isDisplayed()).isTrue();
        String testString = "Tekst przykładowy";
        webForm.fillTextInput(testString);
        String value = webForm.textInput.getAttribute("value");
        if (value.equals(testString)) {
            log.info("Tekst poprawnie wpisany w pole.");
        } else {
            log.error("Tekst niepoprawny, wpisano: '{}'", value);
        }
        String testPassword = "Haslo123_456";
        webForm.fillPasswordInput(testPassword);
        String passwordValue = webForm.passwordInput.getAttribute("value");
        if (passwordValue.equals(testPassword)) {
            log.info("Hasło poprawnie wpisane w pole.");
        } else {
            log.error("Hasło niepoprawne, wpisano: '{}'", passwordValue);
        }
        webForm.fillTextAreaInput(randomGeneratedText.get());
        String randomTextInput = webForm.textArea.getAttribute("value");
        log.info("Wpisano w pole tekstowe: {}", randomTextInput);
        webForm.selectElementOnDropdownList("Two");
        log.info("W pierwszej liście wybrano: {}", webForm.select.getFirstSelectedOption().getText());
        webForm.selectElementOnDataList(DropdownOptions.OPTION_B);
        assertThat(webForm.fileField.isDisplayed()).isTrue();
        webForm.uploadFile(path);
        assertThat(webForm.checkbox1.isDisplayed()).isTrue();
        webForm.checkboxSelector(1);
        webForm.radioSelector(2);
        assertThat(webForm.colorElement.isDisplayed()).isTrue();
        String initialColor = webForm.colorElement.getDomProperty("value");
        webForm.colorPicker(new Color(65, 45, 34, 2).asHex());
        String modifyColor = webForm.colorElement.getDomProperty("value");
        log.info("Początkową wartością koloru było {}, po zmianie było to {}", initialColor, modifyColor);
        webForm.dateSetter(LocalDate.now());
        log.info("Wpisana została data: {}", webForm.dateField.getText());
        assertThat(webForm.submitButton.isDisplayed()).isTrue();
        webForm.submitButton.click();
        wait.until(ExpectedConditions.visibilityOf(webForm.h1SubmitFormConfirmation));
        assertThat(driver.findElement(By.className("lead")).getText()).isEqualTo("Received!");
    }

    @Test(priority = 2, dependsOnMethods ={"mainPageTestElementsVerification"})
    public void navigationPageTest(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, a następnie wypełnienie i przesłanie formularza.
         */
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
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i użycie wybranych dropdownów i kliknięcie w odpowiednie linki.
         */
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
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .
         */
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
            /***
             * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
             * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
             */
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
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         */
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
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         */
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
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         */
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
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         */
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
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         */
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
        log.info("Poczatkowa wysokosc strony to: {}", infiniteScroll.returnPageHeight());
        infiniteScroll.scrollXTimes();
        log.info("Końcowa wysokość strony to: {}", infiniteScroll.returnPageHeight());
    }

    @Test()
    public void shadowRootTest(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         */
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
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         */
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
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         */
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
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         */
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
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         */
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
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         */
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
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         */
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
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         */
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
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         */
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
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony,
         * weryfikację adresu URL oraz tekstu nagłówka, i .//TODO
         */
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

}



