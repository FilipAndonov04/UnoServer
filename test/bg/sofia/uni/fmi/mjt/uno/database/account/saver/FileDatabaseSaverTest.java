package bg.sofia.uni.fmi.mjt.uno.database.account.saver;

import bg.sofia.uni.fmi.mjt.uno.database.account.Account;
import bg.sofia.uni.fmi.mjt.uno.database.account.cipher.StringCipher;
import bg.sofia.uni.fmi.mjt.uno.database.account.saver.exception.DatabaseSavingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FileDatabaseSaverTest {

    private static StringCipher cipher;

    @BeforeAll
    static void setUpTestCase() {
        cipher = Mockito.mock(StringCipher.class);
        when(cipher.encrypt(any())).then(AdditionalAnswers.returnsFirstArg());
    }

    @Test
    void testConstructorWithNullFileName() {
        assertThrows(IllegalArgumentException.class, () -> new FileDatabaseSaver(null, cipher),
            "FileDatabaseSaver's constructor should throw IllegalArgumentException when given null file name!");
    }

    @Test
    void testConstructorWithBlankFileName() {
        assertThrows(IllegalArgumentException.class, () -> new FileDatabaseSaver("", cipher),
            "FileDatabaseSaver's constructor should throw IllegalArgumentException when given blank file name!");
    }

    @Test
    void testConstructorWithNullCipher() {
        assertThrows(IllegalArgumentException.class, () -> new FileDatabaseSaver("name", null),
            "FileDatabaseSaver's constructor should throw IllegalArgumentException when given null cipher!");
    }

    @Test
    void testSaveAccountWithNullAccount() {
        FileDatabaseSaver saver = new FileDatabaseSaver("name", cipher);

        assertThrows(IllegalArgumentException.class, () -> saver.saveAccount(null),
            "SaveAccount should throw IllegalArgumentException when called with null account!");
    }

}
