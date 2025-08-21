package Base.Utils;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Supplier;

public class GenerateRandomText extends Utils{


    public static String randomGeneratedText() {
        return randomGeneratedText(60);
    }

    public static String randomGeneratedText(int textSize) {
        Random random = new Random(2);
        ArrayList<Character> randomTextInput = new ArrayList<>();
        for (char ch = 'a'; ch <= 'z'; ch++) {
            randomTextInput.add(ch);
        }
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            randomTextInput.add(ch);
        }
        randomTextInput.add('.');
        randomTextInput.add(',');
        randomTextInput.add(' ');
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < textSize; i++){
            stringBuilder.append(randomTextInput.get(random.nextInt(randomTextInput.size())));
        }
        return stringBuilder.toString();
    }

}
