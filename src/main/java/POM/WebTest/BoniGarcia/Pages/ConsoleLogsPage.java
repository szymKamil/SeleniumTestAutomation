package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.BiDi;
import org.openqa.selenium.bidi.HasBiDi;
import org.openqa.selenium.bidi.log.*;
import org.openqa.selenium.bidi.module.LogInspector;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.events.ConsoleEvent;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ConsoleLogsPage extends AbstractPage {


    private DevTools devTools;
    CompletableFuture<ConsoleEvent> consoleEvent;
    CompletableFuture<JavascriptException> jsEvent;
    Logger log = LoggerFactory.getLogger(ConsoleLogsPage.class);
    BiDi biDi;

    public ConsoleLogsPage(WebDriver driver, WebDriverWait wait) {
        super();
        PageFactory.initElements(driver, this);
        /*devTools = ((HasDevTools) driver).getDevTools();*/
        if (driver instanceof HasBiDi hasBiDi){
            biDi = hasBiDi.getBiDi();
        }

    }

    //Metody testowe
    public void startListening(){
        //Uruchamiamy nasłuchiwanie konsoli, zanim zostanie załadowana strona
       /* devTools.createSession();
        devTools.send(Log.enable());
        devTools.send(Runtime.enable());
        consoleEvent = new CompletableFuture<>();
        devTools.getDomains().events().addConsoleListener(consoleEvent::complete);
        jsEvent = new CompletableFuture<>();
        devTools.getDomains().events().addJavascriptExceptionListener(jsEvent::complete);*/

    }


    public void getConsoleLogs() throws ExecutionException, InterruptedException, TimeoutException {
/*
        biDi.addListener(new BaseLogEntry(LogLevel.INFO, driver, "", Duration.ofSeconds(15), StackTrace))
*/
        /*log.info(biDi.getBidiSessionStatus().getMessage());
        CompletableFuture<JavascriptLogEntry> futureJSLogs = new CompletableFuture<>();
        LogInspector logInspector = new LogInspector(driver); // Tworzy inspektora logów z BiDi
        List<ConsoleLogEntry> consoleLogs = new ArrayList<>();
        logInspector.onConsoleEntry(consoleLogs::add);
        consoleLogs.forEach(e -> log.info(e.getText()));
        JavascriptLogEntry logEntry = futureJSLogs.get(5, TimeUnit.SECONDS);
        logInspector.onJavaScriptLog(futureJSLogs::complete);
        log.info(logEntry.getText());*/


        /*ConsoleEvent consoleMessage = consoleEvent.get(15, TimeUnit.SECONDS);
        JavascriptException jsEventMsg = jsEvent.get(15, TimeUnit.SECONDS);
        //Informacje o zdarzeniach w konsoli
        log.debug("Console event: {}, {}, {}", consoleMessage.getTimestamp(), consoleMessage.getArgs(), consoleMessage.getMessages());
        log.debug("Console event: {}, {}", jsEventMsg.getMessage(), jsEventMsg.getSystemInformation());*/


// Zmień kolejność: nasłuchiwanie MUSI być ustawione PRZED czekaniem

        log.info(biDi.getBidiSessionStatus().getMessage());

// 1. Ustawienie struktur
        CompletableFuture<JavascriptLogEntry> futureJSLogs = new CompletableFuture<>();
        LogInspector logInspector = new LogInspector(driver);

// 2. Skonfiguruj nasłuchiwanie na logi Konsoli
        List<ConsoleLogEntry> consoleLogs = new ArrayList<>();
        logInspector.onConsoleEntry(consoleLogs::add); // Logi konsoli będą dodawane natychmiast po pojawieniu się

// 3. Skonfiguruj nasłuchiwanie na logi JavaScript
// To jest kluczowe! Ustawiamy, że JAK TYLKO pojawi się log JS, ma on
// ukończyć futureJSLogs i dostarczyć obiekt JavascriptLogEntry.
        logInspector.onJavaScriptLog(futureJSLogs::complete);

// W tym miejscu powinna znaleźć się Twoja akcja na stronie, która generuje log(i), np.:
// driver.findElement(By.id("someId")).click();
// ((JavascriptExecutor) driver).executeScript("console.log('Test JS log');");

// 4. Dopiero teraz czekamy na ukończenie futureJSLogs.
// Ukończenie nastąpi, gdy BiDi przechwyci log JS.
        try {
            JavascriptLogEntry logEntry = futureJSLogs.get(5, TimeUnit.SECONDS);
            log.info("Przechwycony Log JS: " + logEntry.getText());
        } catch (TimeoutException e) {
            log.error("Nie przechwycono logu JavaScript w ciągu 5 sekund.", e);
        }

// 5. Przetwórz przechwycone logi konsoli (jeśli jakieś się pojawiły)
        if (!consoleLogs.isEmpty()) {
            log.info("Przechwycone Logi Konsoli:");
            consoleLogs.forEach(e -> log.info("Typ: " + e.getType() + ", Treść: " + e.getText()));
        } else {
            log.info("Nie przechwycono żadnych logów konsoli.");
        }











    }

}
