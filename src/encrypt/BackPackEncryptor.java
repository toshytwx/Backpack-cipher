package encrypt;

import utils.TripleInt;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

public class BackPackEncryptor implements IEncryptor {
    public int r = -1;
    public int q = -1;
    private List<Integer> openKey = null;

    public BackPackEncryptor() {
        openKey = new ArrayList<>();
    }

    @Override
    public String onEncrypt(List<Integer> key, String incomingText) {
        generateOpenKey(key);
        List<String> cipheredText = new ArrayList<String>();
        char[] chars = incomingText.toCharArray();
        int sum = 0;
        for (int i = 0; i < chars.length; i++)
        {
            char[] charLetter =  new char[1];
            charLetter[0] = incomingText.charAt(i);
            String word = stringToBinary(new String(charLetter).toString());
            char[] wordChars = word.toCharArray();
            for (int j = 0; j < wordChars.length; j++) {
                sum += Integer.valueOf(Integer.valueOf(wordChars[j]) * openKey.get(j));
            }
            cipheredText.add(String.valueOf(sum));
            sum = 0;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Object[] objects = cipheredText.toArray();
        for (int i = 0; i < objects.length; i++) {
            stringBuilder.append(objects[i]).append(" ");
        }
        return stringBuilder.toString();
    }

    @Override
    public String onDecrypt(List<Integer> key, String incomingText) {
        List<Integer> cryptoText = Arrays.stream(incomingText.split("\\s"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        String result = "";
        TripleInt tripleInt = new TripleInt();
        TripleInt gcd = tripleInt.getGCD(r, q);

        int multObratnoe = gcd.getX() % q + q;
        int number;
        for (int word: cryptoText) {
            List<Integer> selectedNumbers = new ArrayList<>();
            number = word * multObratnoe % q;
            while (number > 0)
            {
                int max = findMax(number, key);
                selectedNumbers.add(max);
                number -= max;
            }
            selectedNumbers = selectedNumbers.stream().distinct().collect(Collectors.toList());
            StringBuilder stringOfBits = new StringBuilder();
            for (int num: key) {
                if (selectedNumbers.contains(num)) {
                    stringOfBits.append("1");
                } else {
                    stringOfBits.append("0");
                }
            }
            Byte[] data = getBytesFromBinaryString(stringOfBits.toString());
            byte[] array = new byte[data.length];
            for (int i = 0; i < data.length; i++) {
                array[i] = data[i];
            }
            String dataString = "!";
            try {
                dataString = new String(array, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            result += dataString;
        }
        openKey.subList(0, openKey.size()).clear();
        return result;
    }

    private boolean isPrimeNumber(int x) {
        for (int i = 2; i < x / 2 + 1; i++) {
            if ((x % i) == 0) {
                return false;
            }
        }
        return true;
    }

    private void generateOpenKey(List<Integer> key) {
        int sum = key.stream().mapToInt(Integer::intValue).sum();
        Random random = new Random();
        int simple = sum + 1;
        while (true)
        {
            if (isPrimeNumber(simple)) {
                q = simple;
                break;
            } else {
                simple++;
            }
        }
        while (true) {
            TripleInt tripleInt = new TripleInt();
            tripleInt.setX(0);
            tripleInt.setY(0);
            r = random.nextInt(q - 1) + 1;
            if (tripleInt.getGCD(r, q).getD() == 1) {
                break;
            }
        }
        for (Integer num: key) {
            openKey.add(num * r % q);
        }
    }

    private int findMax(int word, List<Integer> key) {
        List<Integer> result = key.stream().filter(s -> s > word).collect(Collectors.toList());
        return Collections.max(result);
    }

    private String stringToBinary(String data) {
        byte[] bytes = data.getBytes();
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        return sb.toString();
    }

    private Byte[] getBytesFromBinaryString(String binary)
    {
        List<Byte> list = new ArrayList<>();
        for (int i = 0; i < binary.toCharArray().length; i += 8) {
            String t = binary.substring(i, 8);
            list.add(Byte.parseByte(t, 2));
        }
        return (Byte[]) list.toArray();
    }

}
