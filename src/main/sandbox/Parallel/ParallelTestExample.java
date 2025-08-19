package Parallel;

import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class ParallelTestExample extends BaseTest {

    @Test
    public void testGooglePageTitle() {
        getDriver().get("https://www.google.com");
        assertEquals(getDriver().getTitle(), "Google");
        try {
            Thread.sleep(Duration.ofSeconds(5));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testBingPageTitle() {
        getDriver().get("https://www.bing.com");
        assertEquals(getDriver().getTitle(), "Wyszukiwanie — Microsoft Bing");
        try {
            Thread.sleep(Duration.ofSeconds(5));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGooglePageTitle4() {
        getDriver().get("https://www.google.com");
        assertEquals(getDriver().getTitle(), "Google");
        try {
            Thread.sleep(Duration.ofSeconds(5));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testBingPageTitle4() {
        getDriver().get("https://www.bing.com");
        assertEquals(getDriver().getTitle(), "Wyszukiwanie — Microsoft Bing");
        try {
            Thread.sleep(Duration.ofSeconds(5));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGooglePageTitle2() {
        getDriver().get("https://www.google.com");
        assertEquals(getDriver().getTitle(), "Google");
        try {
            Thread.sleep(Duration.ofSeconds(5));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testBingPageTitle2() {
        getDriver().get("https://www.bing.com");
        assertEquals(getDriver().getTitle(), "Wyszukiwanie — Microsoft Bing");
        try {
            Thread.sleep(Duration.ofSeconds(5));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
