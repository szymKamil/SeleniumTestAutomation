package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.BiDi;
import org.openqa.selenium.bidi.HasBiDi;
import org.openqa.selenium.bidi.log.*;
import org.openqa.selenium.bidi.log.Log;
import org.openqa.selenium.bidi.script.ExceptionDetails;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.events.ConsoleEvent;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.concurrent.*;

public class ConsoleLogsPage extends AbstractPage {


	DevTools devTools;
	CompletableFuture<ConsoleEvent> consoleEvent = new CompletableFuture<>();
	CompletableFuture<JavascriptException> jsEvent = new CompletableFuture<>();
	CopyOnWriteArrayList<LogEntry> consoleLogs = new CopyOnWriteArrayList<>();
	CopyOnWriteArrayList<ConsoleEvent> consoleEventList = new CopyOnWriteArrayList<>();
	CopyOnWriteArrayList<LogEntry> consoleJSEventList = new CopyOnWriteArrayList<>();
	CopyOnWriteArrayList<JavascriptException> javascriptExceptions = new CopyOnWriteArrayList<>();
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
			devTools.getDomains()
					.events()
					.addConsoleListener(consoleEvent::complete);
			devTools.getDomains()
					.events()
					.addJavascriptExceptionListener(jsEvent::complete);
			 devTools.getDomains().events().addConsoleListener(e -> consoleEventList.add(e));
			devTools.getDomains()
					.events()
					.addJavascriptExceptionListener(e -> {
						if (!e.getMessage()
								.isEmpty()) {
							javascriptExceptions.add(e);
							log.info("{}", e.getMessage());
						}
					});
		} else if (driver instanceof HasBiDi hasBiDi) {
			log.info("Driver posiada komunikację BiDi");
			log.info("Uruchamiam nasłuch BiDi na konsoli");
			hasBiDi.getBiDi()
					.addListener(Log.entryAdded(), (LogEntry logEntry) -> {
						if (logEntry.getConsoleLogEntry()
								.isPresent()) {
							log.info("Wychwyciłem log w trakcie testu: {}", logEntry.getConsoleLogEntry()
									.get()
									.getText());
							consoleLogs.add(logEntry);
						} else if (logEntry.getJavascriptLogEntry()
								.isPresent()) {
							log.info("Wychwyciłem log JS w trakcie testu: {}", logEntry.getJavascriptLogEntry()
									.get()
									.getText());
							consoleJSEventList.add(logEntry);
						}
					});
		}
	}


	public void getConsoleLogs() {
		log.info("Oto odszukane logi z konsoli: ");
		try {
			if (consoleEvent.isDone() & jsEvent.isDone()) {
				log.info("Złapałem console event o typie {} i treści {}, timesamp: {}, ", consoleEvent.get()
						.getType(), consoleEvent.get()
						.getMessages(), consoleEvent.get()
						.getTimestamp());
				log.info("Złapałem jsEvent event o o treści {}. Przyczyna: {}. StackTrace: {}, ", jsEvent.get()
						.getMessage(), jsEvent.get()
						.getCause(), jsEvent.get()
						.getStackTrace());
			}
		} catch (Exception e) {
			log.info("CompletableFuture nie złapało żadnego z logów.");
		}
		if (consoleEventList.size() > 0) {
			for (ConsoleEvent event : consoleEventList) {
				log.info("W teście znaleziono następujące błędy z konsoli: {}, {}, {}", event.getMessages(), event.getType(), event.getTimestamp());
			}
		}
		try{
			for (JavascriptException exception : javascriptExceptions) {
				log.info("W teście znaleziono następujące błędy JS: {}", exception.getMessage());
			}
		} catch (Exception e){
			log.info("Test nie obsługuje łapania JavascriptException poprzez DevTools");
		}
		try {
			for (LogEntry consLog : consoleLogs) {
				log.info("Log z konsoli: {}, typ: {}, metoda: {}", consLog.getConsoleLogEntry()
						.get()
						.getText(), consLog.getConsoleLogEntry()
						.get()
						.getType(), consLog.getConsoleLogEntry()
						.get()
						.getMethod());
			}
			for (LogEntry consLog : consoleJSEventList) {
				log.info("Log z konsoli JS: {}", consLog.getJavascriptLogEntry()
						.get()
						.getText());
			}
		} catch (Exception e) {
			log.error("❌ Timeout lub błąd: Nie znaleziono oczekiwanego logu lub wystąpił błąd logu.");
		}
	}
}

