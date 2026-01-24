package Base.Utils;


import org.openqa.selenium.WebDriver;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Utils {

    public static WebDriver driver;


    public static boolean testIsInLocalEnv() {
        boolean dockerEnv = Boolean.parseBoolean(System.getProperty("LocalTest"));
        return dockerEnv;
    }

    public static String getCurrentDate(){
        var format =DateTimeFormatter.ofPattern("yyyy-MM-dd--HH_mm_ss");
        return LocalDateTime.now().format(format);
    }


}
