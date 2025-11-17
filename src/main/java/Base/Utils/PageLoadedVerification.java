package Base.Utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class PageLoadedVerification extends Utils{


    private static final Logger log = LoggerFactory.getLogger(PageLoadedVerification.class);

    public static ExpectedCondition<Boolean> pageIsLoaded() {
        return driver -> {
            boolean pageReady = ((JavascriptExecutor) driver)
                    .executeScript("return document.readyState")
                    .equals("complete");

            if (pageReady) {
                log.info("Strona załadowana");
            }
            return pageReady;
        };
    }

}
