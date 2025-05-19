package bg.sofia.uni.fmi.mjt.uno.database.account.cipher;

import java.io.DataInputStream;
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

}
