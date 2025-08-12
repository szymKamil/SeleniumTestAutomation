package Base.Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class CredentialsAES {

    static Scanner scanner;

    public static void main(String[] args) throws Exception {
        System.out.println(encrypt(getTextFromScanner()));

    }


    public static String encrypt(String strToEncode) throws Exception{
        SecretKeySpec keySpec = new SecretKeySpec(System.getenv("SECRET_KEY").getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncode.getBytes()));

    }

    public static String decrypt(String strToDecode) throws Exception{
        SecretKeySpec keySpec = new SecretKeySpec(System.getenv("SECRET_KEY").getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecode)));
    }

    private static String getTextFromScanner(){
        System.out.print("Wpisz dane do zakodowania, lub q do zamknięcia skanera: ");
        scanner = new Scanner(System.in);
        if (scanner.hasNext("q")){
            scanner.close();
        }
        String input = scanner.nextLine();
        return input;
    }


}
