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
			devTools.getDomains()
					.events()
					.addConsoleListener(e -> consoleEventList.add(e));
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

		logIterator(consoleEventList);
		logIterator(javascriptExceptions);
		logIterator(consoleLogs);
		logIterator(consoleJSEventList);
	}


	void logIterator(CopyOnWriteArrayList<?> logArray) {
		try {
			for (Object browserLog : logArray) {
				if (browserLog instanceof LogEntry entry) {
					log.info("Log z konsoli: {}, typ: {}, metoda: {}", entry.getConsoleLogEntry()
							.get()
							.getText(), entry.getConsoleLogEntry()
							.get()
							.getType(), entry.getConsoleLogEntry()
							.get()
							.getMethod());
				} else if (browserLog instanceof ConsoleEvent event) {
					log.info("W teście znaleziono następujące błędy z konsoli: {}, {}, {}", event.getMessages(), event.getType(), event.getTimestamp());
				} else if (browserLog instanceof JavascriptException exception) {
					log.info("Log z konsoli JS: {}", exception.getMessage());
				} else {
					log.info("Nie znaleziono logów.");
				}
			}
		} catch (Exception e){
			log.info("Nie znaleziono logów w tablicy {}", logArray.getClass().getSimpleName());
		}
	}
}






