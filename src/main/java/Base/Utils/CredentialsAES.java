package Base.Utils;

import io.cucumber.java.mk_latn.No;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class CredentialsAES extends Utils {

    static Scanner scanner;



    public static String encrypt(String strToEncode) throws NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, InvalidKeyException {
        SecretKeySpec keySpec = new SecretKeySpec(System.getenv("SECRET_KEY").getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncode.getBytes()));
    }

    public static String decrypt(String strToDecode) throws NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, InvalidKeyException{
        SecretKeySpec keySpec = new SecretKeySpec(System.getenv("SECRET_KEY").getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecode)));
    }

    /**
     * Metoda do szyfrowania ręcznego po wpisaniu hasła w terminalu.
     * @return zwraca zaszyfrowany tekst.
     */
    private static String getTextFromScanner() throws NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, InvalidKeyException {
        System.out.print("Wpisz dane do zakodowania, lub q do zamknięcia skanera: ");
        scanner = new Scanner(System.in);
        if (scanner.hasNext("q")){
            scanner.close();
        }
        String input = scanner.nextLine();
        return encrypt(input);
    }


}
