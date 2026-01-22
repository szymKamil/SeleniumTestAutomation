package Base.BiDi;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.HasBiDi;
import org.openqa.selenium.bidi.log.*;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class BiDiFactory {



	private final Queue<ConsoleLogEntry> consoleLogEntries = new ConcurrentLinkedQueue<>();
	private final Queue<JavascriptLogEntry> javascriptLogEntries = new ConcurrentLinkedQueue<>();

	public  void bidiStartLogging(WebDriver driver){
		if (driver instanceof HasBiDi biDi) {
			biDi.getBiDi().addListener(Log.entryAdded(), this::handleLogs);
		}
	}

	private void handleLogs(LogEntry logEntry){
		if (logEntry.getConsoleLogEntry().isPresent()) {
			consoleLogEntries.add(logEntry.getConsoleLogEntry().get());
		} else if (logEntry.getJavascriptLogEntry().isPresent()) {
			javascriptLogEntries.add(logEntry.getJavascriptLogEntry().get());
		}
	}

	public Map<String, String> snapshotConsoleLogs() {
			return consoleLogEntries.stream().collect(Collectors.toMap(ConsoleLogEntry::getMethod, BaseLogEntry::getText, ( (existing, replacement) -> existing)));
	}
	public Map<String, String> snapshotJSLogs() {
			return javascriptLogEntries.stream().collect(Collectors.toMap(JavascriptLogEntry::getType, BaseLogEntry::getText,  (existing, replacement) -> existing));
	}
}
