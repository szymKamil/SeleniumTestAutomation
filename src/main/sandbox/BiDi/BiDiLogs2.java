package BiDi;

import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.bidi.BiDi;
import org.openqa.selenium.bidi.HasBiDi;
import org.openqa.selenium.bidi.log.*;
import org.openqa.selenium.bidi.module.LogInspector;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.HasLogEvents;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.devtools.events.CdpEventTypes.consoleEvent;


import java.time.Duration;
import java.util.concurrent.*;

public class BiDiLogs2 {

	public static void main(String[] args) {
		testLogow();
		//testlogow2();
		//testlogow3();
	}

		static void testLogow(){
		ChromeOptions options = new ChromeOptions();
		options.setCapability("webSocketUrl", true);
		ChromeDriver chromeDriver = new ChromeDriver(options);
		BiDi bidi = ((HasBiDi) chromeDriver).getBiDi();

		CompletableFuture<LogEntry> completableFutureLogEntry;
		CompletableFuture<LogEntry> javascriptExceptionCompletableFuture;
		CompletableFuture<JavascriptException> javascriptExceptionCompletableFutureError = null;
		CopyOnWriteArrayList<LogEntry> logentryArray = new CopyOnWriteArrayList<>();

			//Uruchomienie nasłuchiwania
			completableFutureLogEntry = new CompletableFuture<>();
			javascriptExceptionCompletableFuture = new CompletableFuture<>();


		bidi.addListener(Log.entryAdded(), logEntry -> {
			if (logEntry.getConsoleLogEntry().isPresent()){
				logentryArray.add(logEntry);
			}
		});


			bidi.addListener(Log.entryAdded(), logEntry -> {
				if (logEntry.getConsoleLogEntry().isPresent()){
					completableFutureLogEntry.complete(logEntry);
				}
			});

			bidi.addListener(Log.entryAdded(), logEntry -> {
				if (logEntry.getConsoleLogEntry().isPresent() && logEntry.getConsoleLogEntry().get().getLevel() == LogLevel.ERROR){
					javascriptExceptionCompletableFuture.complete(logEntry);
				}
			});

			bidi.addListener(Log.entryAdded(), logEntry -> {
			// Sprawdzamy czy to log konsolowy (console.error/warn)
			if (logEntry.getConsoleLogEntry().isPresent()) {
				var consoleLog = logEntry.getConsoleLogEntry()
						.get();
				if (consoleLog.getLevel() == LogLevel.ERROR || consoleLog.getLevel() == LogLevel.WARNING) {
					System.out.println("Złapano console: " + consoleLog.getLevel() + ": " + consoleLog.getText());
				}
			}});

		bidi.addListener(Log.entryAdded(), logEntry -> {
			if (logEntry.getConsoleLogEntry().isPresent()){
				System.out.println("Log z konsoli: " + logEntry.getConsoleLogEntry().get().getText());
			}
		});
		try {
			bidi.addListener(Log.entryAdded(), logEntry -> {
				if (logEntry.getJavascriptLogEntry()
						.isPresent()) {
					System.out.println("Log z konsoli JAVASCRIPT: " + logEntry.getJavascriptLogEntry()
							.get()
							.getText());
				}
			});
		} catch (Exception e) {

		}


			try {
				javascriptExceptionCompletableFuture.get(10, TimeUnit.SECONDS);
				completableFutureLogEntry.get(10, TimeUnit.SECONDS);
				System.out.println(completableFutureLogEntry.get().getConsoleLogEntry().get().getText());
				System.out.println(javascriptExceptionCompletableFuture.get().getConsoleLogEntry().get().getText());
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			} catch (TimeoutException e) {
				throw new RuntimeException(e);
			}
			JavascriptExecutor js = (JavascriptExecutor) chromeDriver;
			try {
				js.executeScript("return notExistingVar;");
				js.executeScript("console.error('To jest błąd, ale nie rzucam wyjątku');");
			} catch (Exception e){
				e.getMessage();
			}

		//try (LogInspector logInspector = new LogInspector(driver)) {
		//  logInspector.onConsoleEntry(log -> System.out.println("Log: " + log.getText()));


			System.out.println("---".repeat(3));

			for (LogEntry logEntry : logentryArray){
				System.out.println("Pętla po logach w tablicy: " + logEntry.getConsoleLogEntry().get().getText());
				System.out.println("Ten log ma poziom: " + logEntry.getConsoleLogEntry().get().getLevel());
				System.out.println("Ten log dotyczy metody: " + logEntry.getConsoleLogEntry().get().getMethod());
			}

			/*// Sprawdzamy czy to wyjątek JS (rzucany przez silnik)
			if (logEntry.getJavascriptLogEntry().isPresent()) {
				var jsLog = logEntry.getJavascriptLogEntry().get();
				if (jsLog.getLevel() == LogLevel.ERROR) {
					System.out.println("Złapano wyjątek JS: " + jsLog.getText());
					// javascriptExceptionCompletableFutureError.complete(jsLog);
				}
			}*/


		chromeDriver.quit();
	}

	static void testlogow2(){
		System.out.println("Uruchamiam test logow 2");
		ChromeOptions options = new ChromeOptions();
		options.setCapability("webSocketUrl", true);
		ChromeDriver chromeDriver = new ChromeDriver(options);
		BiDi bidi = ((HasBiDi) chromeDriver).getBiDi();
		WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(15));

		CopyOnWriteArrayList<String> logentryArray = new CopyOnWriteArrayList<>();
		((HasLogEvents) chromeDriver).onLogEvent(consoleEvent(e -> logentryArray.add(e.getMessages().get(0))));

		chromeDriver.get("https://bonigarcia.dev/selenium-webdriver-java/console-logs.html");


		wait.until(_d -> logentryArray.size() == 4);
		for (String log : logentryArray){
			System.out.println("Log złapany to: " + log);
		}

		chromeDriver.quit();
	}


	static void testlogow3(){
		System.out.println("Uruchamiam test logow 3");
		ChromeOptions options = new ChromeOptions();
		options.setCapability("webSocketUrl", true);
		ChromeDriver chromeDriver = new ChromeDriver(options);
		BiDi bidi = ((HasBiDi) chromeDriver).getBiDi();
		WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(20));

		CopyOnWriteArrayList<String> logentryArray = new CopyOnWriteArrayList<>();
		try (LogInspector logs = new LogInspector(chromeDriver)){
			logs.onLog(log -> System.out.println("Oto generyczny log: " + log.getConsoleLogEntry().map(entry -> entry.getText())
					.orElse("brak tekstu")));
			logs.onLog(log -> logentryArray.add(log.getConsoleLogEntry().map(entry -> entry.getText())
					.orElse("brak tekstu")));
		}

			 chromeDriver.get("https://bonigarcia.dev/selenium-webdriver-java/console-logs.html");


//		wait.until(_d -> logentryArray.size() == 1);
		for (String log : logentryArray){
			System.out.println("Log złapany to: " + log);
		}

		chromeDriver.quit();
	}


}




/*
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