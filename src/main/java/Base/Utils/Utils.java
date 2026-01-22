package Base.Utils;

import Base.Drivers.DriverFactory;
import org.openqa.selenium.WebDriver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils {

    public static WebDriver driver;


    public static boolean testIsInLocalEnv() {
        boolean dockerEnv = Boolean.parseBoolean(System.getProperty("LocalTest"));
        return dockerEnv;
    }

    public static String getCurrentDate(){
        var format =DateTimeFormatter.ofPattern("yyyy-MM-dd--HH:mm:ss");
        return LocalDateTime.now().format(format);
    }


}
