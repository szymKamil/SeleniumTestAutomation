package POM.WebTest.BoniGarcia.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeStampGenerator {

    public static String generateDateTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("_yyyy-MM-dd-hh-mm-ss"));
    }

}
