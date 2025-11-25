package Base.Utils;

import Base.Drivers.DriverFactory;
import org.openqa.selenium.WebDriver;

public class Utils {

    public static WebDriver driver;

    public static void setUtilityDriver(WebDriver driver){
        Utils.driver = DriverFactory.getDriver();
    }

    public static boolean testIsInDockerEnv() {
        boolean dockerEnv = Boolean.parseBoolean(System.getProperty("LocalTest"));
        return dockerEnv;
    }


}
