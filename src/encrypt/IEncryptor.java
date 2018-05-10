package encrypt;

import java.util.List;

public interface IEncryptor {
    String onEncrypt(List<Integer> key, String incomingText);
    String onDecrypt(List<Integer> key, String incomingText);
}
