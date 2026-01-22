package Base.Utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;

public class PageLoadedVerification extends Utils{


    private static final Logger log = LoggerFactory.getLogger(PageLoadedVerification.class);

    public static ExpectedCondition<Boolean> pageIsLoaded() {
        return driver -> {
			assert driver != null;
			boolean pageReady = Objects.equals(((JavascriptExecutor) driver)
					.executeScript("return document.readyState"), "complete");
            if (pageReady) {
                log.info("Strona załadowana");
            }
            return pageReady;
        };
    }

}
