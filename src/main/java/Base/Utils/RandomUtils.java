package Base.Utils;


import java.util.Random;
import java.util.stream.Collectors;

public class RandomUtils extends Utils {

    static Random random = new Random();

    public static int getRandomInt(int min, int max){
        return random.nextInt(min, max);
    }



}
