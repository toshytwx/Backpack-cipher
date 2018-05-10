import encrypt.BackPackEncryptor;

import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchPaddingException {
            BackPackEncryptor backPackEncryptor = new BackPackEncryptor();
            Controller controller = new Controller(backPackEncryptor);
            MyForm form = new MyForm(controller);
            form.pack();
            form.setVisible(true);
            System.exit(0);
    }
}
