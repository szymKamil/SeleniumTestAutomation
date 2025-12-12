package POM.WebTest.BoniGarcia.Pages;

import org.apache.hc.core5.util.Timeout;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.BiDi;
import org.openqa.selenium.bidi.HasBiDi;
import org.openqa.selenium.bidi.log.*;
import org.openqa.selenium.bidi.log.Log;
import org.openqa.selenium.bidi.module.LogInspector;
import org.openqa.selenium.bidi.module.Network;
import org.openqa.selenium.bidi.network.AddInterceptParameters;
import org.openqa.selenium.bidi.network.BeforeRequestSent;
import org.openqa.selenium.bidi.network.InterceptPhase;
import org.openqa.selenium.bidi.network.UrlPattern;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.events.ConsoleEvent;
import org.openqa.selenium.logging.Logs;
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
        if (driver instanceof HasDevTools hasDevTools) {
            devTools = hasDevTools.getDevTools();
            log.info("Uruchamiamy nasłuchiwanie w DevTools");
        } else if (driver instanceof HasBiDi hasBiDi) {
            biDi = hasBiDi.getBiDi();
            log.info("Uruchamiamy nasłuchiwanie w BiDi");
        } else {
            log.error("Test nie może zostać uruchomiony, driver nie obsługuje narzędzi nasłuchujących logów.");
        }

    }

    //Metody testowe
    public void startListening() {
        //Uruchamiamy nasłuchiwanie konsoli, zanim zostanie załadowana strona
        if (driver instanceof HasDevTools hasDevTools) {
            log.info("Driver jest instancją DevTools");
            hasDevTools.getDevTools()
                    .createSession();
            hasDevTools.getDevTools()
                    .send(org.openqa.selenium.devtools.v142.log.Log.enable());
            hasDevTools.getDevTools()
                    .send(org.openqa.selenium.devtools.v142.runtime.Runtime.enable());
            consoleEvent = new CompletableFuture<>();
            devTools.getDomains()
                    .events()
                    .addConsoleListener(consoleEvent::complete);
            jsEvent = new CompletableFuture<>();
            devTools.getDomains()
                    .events()
                    .addJavascriptExceptionListener(jsEvent::complete);
        } else if (driver instanceof HasBiDi hasBiDi) {
            log.info("Driver posiada komunikację BiDi");
            logEntryCompletableFuture = new CompletableFuture<>();
            jsLogs = new CopyOnWriteArrayList<>();
            ((HasBiDi) driver).getBiDi()
                    .addListener(Log.entryAdded(), (LogEntry logEntry) -> {
                        log.info("Uruchamiam nasłuch BiDi na konsoli");
                        if (!logEntryCompletableFuture.isDone()) {
                            logEntryCompletableFuture.complete(logEntry);
                            log.info("Znalazłem log: {}", logEntry.getConsoleLogEntry()
                                    .get()
                                    .getText());

                        }
                    });

/*
            hasBiDi.getBiDi().addListener(Log.entryAdded(), logEntry -> {logEntry.getJavascriptLogEntry()})
*/

            /*hasBiDi.getBiDi().addListener(new Network(driver).addIntercept(new AddInterceptParameters(InterceptPhase.BEFORE_REQUEST_SENT).urlPattern(new UrlPattern().hostname())), (BeforeRequestSent event) -> {
                System.out.println("URL Requestu: " + event.getRequest().getUrl());
                System.out.println("Metoda: " + event.getRequest().getMethod());
            });*/

            /*hasBiDi.getBiDi().addListener(Log.entryAdded(), (LogEntry jsLogEntry) ->{
                if (jsLogEntry.getJavascriptLogEntry().isPresent()){
                    log.info("Uruchamiam nasłuch BiDi na konsoli JS");
                    jsLogs.add(jsLogEntry.getJavascriptLogEntry().toString());
                    log.info("Znalazłem log: {}", jsLogEntry.getJavascriptLogEntry().get().getText());
                }
            });*/
          /*  try (LogInspector logInspector = new LogInspector(driver)) {
                logInspector.onConsoleEntry(log -> System.out.println("Log: " + log.getText()));
            }*/
        }



   /* public void getConsoleLogs() throws ExecutionException, InterruptedException, TimeoutException {
        try {
        logEntryCompletableFuture.get(15, TimeUnit.SECONDS);
            wait.until(_d -> jsLogs.size() == 3);
            if (logEntryCompletableFuture.get().getConsoleLogEntry().isPresent()){
                logEntryCompletableFuture.get().getConsoleLogEntry()
						.ifPresent(e ->  log.info("Log z konsoli {}", e.getText()));
            }

              jsLogs.iterator().forEachRemaining((e -> log.info("Log z konsoli JS: {}", e.toString())));
        } catch (Exception e) {
            log.error("❌ Timeout lub błąd: Nie znaleziono oczekiwanego logu w ciągu 5 sekund.");
        }
    }*/
    }
}
