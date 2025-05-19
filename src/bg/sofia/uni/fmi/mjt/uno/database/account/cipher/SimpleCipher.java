package bg.sofia.uni.fmi.mjt.uno.database.account.cipher;

import java.util.Random;

public class SimpleCipher implements StringCipher {

    public static final int MAX_KEY_VALUE = 1000;

    private static final Random RANDOM_GENERATOR = new Random();

    private final int key;

    public static int generateKey() {
        return RANDOM_GENERATOR.nextInt(MAX_KEY_VALUE);
    }

    public SimpleCipher(int key) {
        key %= MAX_KEY_VALUE;
        if (key < 0) {
            key += MAX_KEY_VALUE;
        }

        this.key = key;
    }

    @Override
    public String encrypt(String data) {
        assertNotNull(data);

        StringBuilder builder = new StringBuilder(data);
        for (int i = 0; i < builder.length(); i++) {
            builder.setCharAt(i, (char) (builder.charAt(i) + key));
        }
        return builder.toString();
    }

    @Override
    public String decrypt(String data) {
        assertNotNull(data);

        StringBuilder builder = new StringBuilder(data);
        for (int i = 0; i < builder.length(); i++) {
            builder.setCharAt(i, (char) (builder.charAt(i) - key));
        }
        return builder.toString();
    }

    public int getKey() {
        return key;
    }

    private void assertNotNull(String data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
    }

}
