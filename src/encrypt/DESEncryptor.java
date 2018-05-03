package encrypt;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Arrays;

public class DESEncryptor implements IEncryptor {
    private static SecretKey key;
    private Cipher desCipher;
    private byte[] encryptedBytes;

    @Override
    public String onEncryptDecrypt(String incomingText, int mode) {
        String result = "";
        try {
            desCipher.init(mode, key);
            switch (mode) {
                case Cipher.ENCRYPT_MODE:
                {
                    byte[] incomingTextInBytes = incomingText.getBytes();
                    encryptedBytes = desCipher.doFinal(incomingTextInBytes);
                    result = new String(encryptedBytes);
                    break;
                }
                case Cipher.DECRYPT_MODE:
                {
                    byte[] decryptedTextInBytes = desCipher.doFinal(encryptedBytes);
                    result = new String(decryptedTextInBytes);
                    break;
                }
            }
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void setKey(SecretKey key) {
        DESEncryptor.key = key;
    }

    public static SecretKey getKey() {return key;}

    public void setDesCipher(Cipher cipher) {
        this.desCipher = cipher;
    }
}
