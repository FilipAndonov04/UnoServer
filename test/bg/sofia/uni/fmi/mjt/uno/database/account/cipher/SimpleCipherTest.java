package bg.sofia.uni.fmi.mjt.uno.database.account.cipher;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleCipherTest {

    @Test
    void testKeyIsBetween0andMaxNumber() {
        int key = SimpleCipher.generateKey();

        assertTrue(key >= 0 && key < SimpleCipher.MAX_KEY_VALUE,
            "Key value must be between 0 and MAX_KEY_VALUE!");
    }

    @Test
    void testEncryptWithNullData() {
        SimpleCipher cipher = new SimpleCipher(0);

        assertThrows(IllegalArgumentException.class, () -> cipher.encrypt(null),
            "Encrypt should throw when called with null data!");
    }

    @Test
    void testDecryptWithNullData() {
        SimpleCipher cipher = new SimpleCipher(0);

        assertThrows(IllegalArgumentException.class, () -> cipher.decrypt(null),
            "Decrypt should throw when called with null data!");
    }

    @Test
    void testEncryptWithValidData() {
        SimpleCipher cipher = new SimpleCipher(1);

        assertEquals("bc4Y", cipher.encrypt("ab3X"), "Encrypt should encrypt correctly!");
    }

    @Test
    void testEncryptWithValidData2() {
        SimpleCipher cipher = new SimpleCipher(2);

        assertEquals("cd5Z", cipher.encrypt("ab3X"), "Encrypt should encrypt correctly!");
    }

    @Test
    void testDecryptWithValidData() {
        SimpleCipher cipher = new SimpleCipher(1);

        assertEquals("ab3X", cipher.decrypt("bc4Y"), "Decrypt should decrypt correctly!");
    }

    @Test
    void testDecryptWithValidData2() {
        SimpleCipher cipher = new SimpleCipher(2);

        assertEquals("ab3X", cipher.decrypt("cd5Z"), "Decrypt should decrypt correctly!");
    }

}
