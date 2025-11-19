package Parallel;

public class BaseTest {

//    private static ThreadLocal<WebDriver> driverThread2 = new ThreadLocal<>();
//    private static ThreadLocal<WebDriverWait> waitThread = new ThreadLocal<>();
//    LoginFormPage loginPageTest;
//
//    @BeforeMethod
//    public void setUp() throws InterruptedException {
//        WebDriver driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driverThread2.set(driver);
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//        waitThread.set(wait);
//        loginPageTest = new LoginFormPage(driverThread2.get(), waitThread.get());
//
//    }
//
//
//
//    @AfterMethod
//    public void tearDown() {
//        // Pobranie sterownika, zamknięcie go i usunięcie z ThreadLocal
//        WebDriver driver = driverThread2.get();
//        if (driver != null) {
//            driver.quit();
//            driverThread2.remove();
//        }
//    }
}