package BiDi;

import org.openqa.selenium.bidi.log.Log;
import org.openqa.selenium.bidi.log.LogEntry;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class BiDiLogs2 {





        /*
        biDi.addListener(new BaseLogEntry(LogLevel.INFO, driver, "", Duration.ofSeconds(15), StackTrace))



        /*biDi.addListener( Log.entryAdded(), (LogEntry logentry) -> {
            System.out.println("Oto log entry: " + logentry.getConsoleLogEntry().get());
            log.info("{}", logentry.getJavascriptLogEntry().toString());
        } );

	CompletableFuture<LogEntry> logFuture = new CompletableFuture<>();

        biDi.addListener(Log.entryAdded(), (LogEntry logEntry) -> {
		// Tutaj możesz dodać warunek, np. szukając konkretnego tekstu,
		// poziomu logowania (np. ERROR) lub typu (Console/JS).
		if (logEntry.getConsoleLogEntry().isPresent()) {
			// Log został znaleziony - Zakończ oczekiwanie
			logFuture.complete(logEntry);
		}
	});
        try {
		LogEntry znalezionyLog = logFuture.get(15, TimeUnit.SECONDS);
		System.out.println("✅ Log ZNALEZIONY: " + znalezionyLog.getConsoleLogEntry().get().getText());
	} catch (Exception e) {
		System.err.println("❌ Timeout lub błąd: Nie znaleziono oczekiwanego logu w ciągu 5 sekund.");
	}

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

/*
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
        }*/







}
