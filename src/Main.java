import encrypt.DESEncryptor;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchPaddingException {
        try {
            DESEncryptor desEncryptor = specializeEncryptor();
            Controller controller = new Controller(desEncryptor);
            MyForm form = new MyForm(controller);
            form.pack();
            form.setVisible(true);
            System.exit(0);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static DESEncryptor specializeEncryptor() throws NoSuchAlgorithmException, NoSuchPaddingException {
        KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
        SecretKey desKey = keygenerator.generateKey();
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        DESEncryptor desEncryptor = new DESEncryptor();
        DESEncryptor.setKey(desKey);
        desEncryptor.setDesCipher(cipher);
        return desEncryptor;
    }
}
