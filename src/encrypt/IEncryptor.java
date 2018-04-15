package encrypt;

import javax.crypto.SecretKey;

public interface IEncryptor {
    String onEncryptDecrypt(String incomingText, int decryptMode);
}
