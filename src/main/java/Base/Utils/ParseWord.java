package Base.Utils;

import java.util.Map;

public class ParseWord {

    private static final Map<String, Integer> WORD_TO_NUMBER = Map.of(
            "one", 1,
            "two", 2,
            "three", 3,
            "four", 4,
            "five", 5
    );

    /**
     * Utils pozwalający na zamianę wartości tekstowej na liczbę.
     * @param word wartości liczbowa w formie tekstowe.
     * @return zwraca int podanej wartości tekstowej.
     */
    public static Integer parseWord(String word) {
        return WORD_TO_NUMBER.get(word.toLowerCase());
    }

}
