package Base.Utils;

import java.util.Random;

public class RandomUtils extends Utils {

    static Random random = new Random();

    public static int getRandomInt(int min, int max){
        return random.nextInt(min, max);
    }



}
