package Base.Utils;

import java.util.Random;

public class RandomUtils extends Utils {

    static Random random = new Random();

    /**
     * Zwraca losową liczbę z zakresu wyznaczonego parametrami.
     * @param min min
     * @param max max
     * @return zwracany int
     */
    public static int getRandomInt(int min, int max){
        return random.nextInt(min, max);
    }


}
