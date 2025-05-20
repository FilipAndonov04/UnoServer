package bg.sofia.uni.fmi.mjt.uno.database.account.cipher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public interface StringCipher {

    /**
     * Encrypts data
     *
     * @param data String to be encrypted
     * @return The encrypted data
     */
    String encrypt(String data);

    /**
     * Decrypts data
     *
     * @param data String to be decrypted
     * @return The decrypted data
     */
    String decrypt(String data);

    static StringCipher getSimpleCipherFromStream(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            int key = dataInputStream.readInt();
            return new SimpleCipher(key);
        }
    }

    static void saveSimpleKeyInFile(int key, String fileName) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(fileName))) {
            dataOutputStream.writeInt(key);
        }
    }

    /**
     * Creates a file with random encryption key
     *
     * @throws IOException If error occurred
     */
    public static void main(String[] args) throws IOException {
        saveSimpleKeyInFile(SimpleCipher.generateKey(), "secret.key");
    }

}
