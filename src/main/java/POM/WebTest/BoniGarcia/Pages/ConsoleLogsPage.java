package POM.WebTest.BoniGarcia.Pages;

import org.apache.hc.core5.util.Timeout;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.BiDi;
import org.openqa.selenium.bidi.HasBiDi;
import org.openqa.selenium.bidi.log.*;
import org.openqa.selenium.bidi.log.Log;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
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


    DevTools devTools;
    CompletableFuture<ConsoleEvent> consoleEvent;
    CompletableFuture<LogEntry> logEntryCompletableFuture;
    CompletableFuture<JavascriptException> jsEvent;
    CopyOnWriteArrayList<String> jsLogs;
    Logger log = LoggerFactory.getLogger(ConsoleLogsPage.class);
    BiDi biDi;

    public ConsoleLogsPage(WebDriver driver, WebDriverWait wait) {
        super();
        PageFactory.initElements(driver, this);
        if (driver instanceof HasDevTools hasDevTools){
            devTools = hasDevTools.getDevTools();
            log.info("Uruchamiamy nasłuchiwanie w DevTools");
        } else if (driver instanceof HasBiDi hasBiDi){
            biDi = hasBiDi.getBiDi();
            log.info("Uruchamiamy nasłuchiwanie w BiDi");
        } else {
            log.error("Test nie może zostać uruchomiony, driver nie obsługuje narzędzi nasłuchujących logów.");
        }

    }

    //Metody testowe
    public void startListening(){
        //Uruchamiamy nasłuchiwanie konsoli, zanim zostanie załadowana strona
        if (driver instanceof DevTools hasDevTools){
            hasDevTools.createSession();
            hasDevTools.send(org.openqa.selenium.devtools.v142.log.Log.enable());
            hasDevTools.send(org.openqa.selenium.devtools.v142.runtime.Runtime.enable());
            consoleEvent = new CompletableFuture<>();
            devTools.getDomains().events().addConsoleListener(consoleEvent::complete);
            jsEvent = new CompletableFuture<>();
            devTools.getDomains().events().addJavascriptExceptionListener(jsEvent::complete);
        } else if (driver instanceof HasBiDi hasBiDi) {
            logEntryCompletableFuture = new CompletableFuture<>();
            jsLogs = new CopyOnWriteArrayList<>();
            hasBiDi.getBiDi().addListener(Log.entryAdded(), (LogEntry logEntry) ->{
                log.info("Uruchamiam nasłuch BiDi na konsoli");
                if (!logEntryCompletableFuture.isDone()) {
                    logEntryCompletableFuture.complete(logEntry);
                    log.info("Znalazłem log: {}", logEntry.getConsoleLogEntry().get().getText());
                }
            });
            hasBiDi.getBiDi().addListener(Log.entryAdded(), (LogEntry jsLogEntry) ->{
                if (jsLogEntry.getJavascriptLogEntry().isPresent()){
                    log.info("Uruchamiam nasłuch BiDi na konsoli JS");
                    jsLogs.add(jsLogEntry.getJavascriptLogEntry().toString());
                    log.info("Znalazłem log: {}", jsLogEntry.getJavascriptLogEntry().get().getText());
                }
            });
        }
    }


    public void getConsoleLogs() throws ExecutionException, InterruptedException, TimeoutException {
        try {
            // KROK 1: Czekamy chwilę, aby upewnić się, że strona załadowała skrypty i logi spłynęły.
            // Jeśli wiesz, że logi są generowane natychmiast, wait.until może sprawdzać size > 0
            try {
                // Czekamy na jakikolwiek log (max 5 sekund), żeby nie puszczać pętli na pustych listach od razu
                anyLogReceived.get(5, TimeUnit.SECONDS);

                // Opcjonalnie: Czekamy dodatkowo chwilę, aż reszta "doleci" (async)
                Thread.sleep(500);
            } catch (TimeoutException e) {
                log.warn("Nie otrzymano żadnych logów w ciągu 5 sekund (strona może być czysta).");
            }

            // KROK 2: Wypisanie Logów Konsoli
            log.info("=== ZESTAWIENIE LOGÓW KONSOLI (" + consoleLogs.size() + ") ===");
            for (ConsoleLogEntry entry : consoleLogs) {
                log.info("[{}] {}", entry.getLevel(), entry.getText());
            }

            // KROK 3: Wypisanie Błędów JS
            log.info("=== ZESTAWIENIE BŁĘDÓW JS (" + jsExceptions.size() + ") ===");
            for (JavascriptLogEntry entry : jsExceptions) {
                log.info("[JS ERROR] {}", entry.getText());
            }

            // Assertions (opcjonalnie)
            // Jeśli oczekujesz co najmniej 1 błędu JS (bo masz Uncaught Error):
            if (jsExceptions.isEmpty()) {
                log.error("Oczekiwano błędu JS, ale lista jest pusta!");
            }

        } catch (Exception e) {
            log.error("Wystąpił błąd podczas pobierania logów: ", e);
        }



        try {
        logEntryCompletableFuture.get(15, TimeUnit.SECONDS);
            wait.until(_d -> jsLogs.size() > 0);
            if (logEntryCompletableFuture.get().getConsoleLogEntry().isPresent()){
                logEntryCompletableFuture.get().getConsoleLogEntry()
						.ifPresent(e ->  log.info("Log z konsoli {}", e.getText()));
            }

              jsLogs.iterator().forEachRemaining((e -> log.info("Log z konsoli JS: {}", e.toString())));
        } catch (Exception e) {
            log.error("❌ Timeout lub błąd: Nie znaleziono oczekiwanego logu w ciągu 5 sekund.");
        }
    }
}
