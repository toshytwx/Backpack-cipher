import encrypt.BackPackEncryptor;
import encrypt.IEncryptor;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;

public class Controller {
    private IEncryptor encryptor;

    public Controller(IEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    public String onEncryptDecrypt(String incomingText, int decryptMode) {
//        return encryptor.onEncryptDecrypt(incomingText, decryptMode);
        return null;
    }

    public String onOpenFile(JFileChooser jFileChooser) {
        StringBuilder sb = new StringBuilder();
        int res = jFileChooser.showDialog(null, "Open file");
        if (res == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                int c;
                while ((c = reader.read()) != -1) {
                    sb.append((char) c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public void onSaveFile(JFileChooser jFileChooser, String input) {
        int res = jFileChooser.showDialog(null, "Save file");
        if (res == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            jFileChooser.setSelectedFile(file);
            try (BufferedWriter writer = new BufferedWriter((new FileWriter(file)))) {
                writer.write(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void specializeEncryptor(String desMode) {
        try {
            Cipher cipher = Cipher.getInstance("DES" + desMode);
//            ((BackPackEncryptor) encryptor).setDesCipher(cipher);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
