package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.events.ConsoleEvent;
import org.openqa.selenium.devtools.v138.log.Log;
import org.openqa.selenium.devtools.v138.runtime.Runtime;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConsoleLogsPage extends AbstractPage {


    private DevTools devTools;
    CompletableFuture<ConsoleEvent> consoleEvent;
    CompletableFuture<JavascriptException> jsEvent;

    public ConsoleLogsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
        devTools = ((HasDevTools) driver).getDevTools();
    }

    //Metody testowe
    public void startListening(){
        //Uruchamiamy nasłuchiwanie konsoli, zanim zostanie załadowana strona
        devTools.createSession();
        devTools.send(Log.enable());
        devTools.send(Runtime.enable());
        consoleEvent = new CompletableFuture<>();
        devTools.getDomains().events().addConsoleListener(consoleEvent::complete);
        jsEvent = new CompletableFuture<>();
        devTools.getDomains().events().addJavascriptExceptionListener(jsEvent::complete);
    }


    public void getConsoleLogs() throws ExecutionException, InterruptedException, TimeoutException {
        ConsoleEvent consoleMessage = consoleEvent.get(15, TimeUnit.SECONDS);
        JavascriptException jsEventMsg = jsEvent.get(15, TimeUnit.SECONDS);
        //Informacje o zdarzeniach w konsoli
        log.debug("Console event: {}, {}, {}", consoleMessage.getTimestamp(), consoleMessage.getArgs(), consoleMessage.getMessages());
        log.debug("Console event: {}, {}", jsEventMsg.getMessage(), jsEventMsg.getSystemInformation());
    }

}
