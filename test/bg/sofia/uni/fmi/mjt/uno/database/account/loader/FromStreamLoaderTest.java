package bg.sofia.uni.fmi.mjt.uno.database.account.loader;

import bg.sofia.uni.fmi.mjt.uno.database.account.Account;
import bg.sofia.uni.fmi.mjt.uno.database.account.cipher.StringCipher;
import bg.sofia.uni.fmi.mjt.uno.database.account.loader.exception.DatabaseLoadingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;

import java.io.Reader;
import java.io.StringReader;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FromStreamLoaderTest {

    private static StringCipher cipher;

    @BeforeAll
    static void setUpTestCase() {
        cipher = Mockito.mock(StringCipher.class);
        when(cipher.decrypt(any())).then(AdditionalAnswers.returnsFirstArg());
    }

    @Test
    void testConstructorWithNullReader() {
        assertThrows(IllegalArgumentException.class, () -> new FromStreamLoader(null, cipher),
            "FromStreamLoader's constructor should throw IllegalArgumentException when given null reader!");
    }

    @Test
    void testConstructorWithNullCipher() {
        assertThrows(IllegalArgumentException.class, () -> new FromStreamLoader(Mockito.mock(Reader.class), null),
            "FromStreamLoader's constructor should throw IllegalArgumentException when given null cipher!");
    }

    @Test
    void testLoadAccountsWithEmptyReader() throws DatabaseLoadingException {
        Reader reader = new StringReader("");
        FromStreamLoader loader = new FromStreamLoader(reader, cipher);

        assertTrue(loader.loadAccounts().isEmpty(),
            "LoadAccounts should return an empty set when reading a empty stream!");
    }

    @Test
    void testLoadAccountsWithReaderWithOneLine() throws DatabaseLoadingException {
        Reader reader = new StringReader("username=name password=pass");
        FromStreamLoader loader = new FromStreamLoader(reader, cipher);

        Account account = new Account("name", "pass");
        assertEquals(Set.of(account), loader.loadAccounts(),
            "LoadAccounts should read accounts correctly!");
    }

    @Test
    void testLoadAccountsWithReaderWithInvalidLineWithNoEqualSignInTheUsername() {
        Reader reader = new StringReader("usernamename password=pass");
        FromStreamLoader loader = new FromStreamLoader(reader, cipher);

        Account account = new Account("name", "pass");
        assertThrows(DatabaseLoadingException.class, loader::loadAccounts,
            "LoadAccounts should throw DatabaseLoadingException when reading an invalid line!");
    }

    @Test
    void testLoadAccountsWithReaderWithInvalidLineWithNoSpaceBetweenWords() {
        Reader reader = new StringReader("username=namepassword=pass");
        FromStreamLoader loader = new FromStreamLoader(reader, cipher);

        Account account = new Account("name", "pass");
        assertThrows(DatabaseLoadingException.class, loader::loadAccounts,
            "LoadAccounts should throw DatabaseLoadingException when reading an invalid line!");
    }

    @Test
    void testLoadAccountsWithReaderWithInvalidLineWithNoPassword() {
        Reader reader = new StringReader("username=name");
        FromStreamLoader loader = new FromStreamLoader(reader, cipher);

        Account account = new Account("name", "pass");
        assertThrows(DatabaseLoadingException.class, loader::loadAccounts,
            "LoadAccounts should throw DatabaseLoadingException when reading an invalid line!");
    }

    @Test
    void testLoadAccountsWithReaderWithThreeLines() throws DatabaseLoadingException {
        String text = "username=name password=pass" + System.lineSeparator() +
            "username=name2 password=pass2" + System.lineSeparator() + "username=name3 password=pass3";
        Reader reader = new StringReader(text);

        FromStreamLoader loader = new FromStreamLoader(reader, cipher);

        Account account = new Account("name", "pass");
        Account account2 = new Account("name2", "pass2");
        Account account3 = new Account("name3", "pass3");
        assertEquals(Set.of(account, account2, account3), loader.loadAccounts(),
            "LoadAccounts should read accounts correctly!");
    }

    @Test
    void testLoadAccountsWithReaderWithThreeLinesWithEndLineAtTheEnd() throws DatabaseLoadingException {
        String text = "username=name password=pass" + System.lineSeparator() + "username=name2 password=pass2" +
            System.lineSeparator() + "username=name3 password=pass3" + System.lineSeparator();
        Reader reader = new StringReader(text);

        FromStreamLoader loader = new FromStreamLoader(reader, cipher);

        Account account = new Account("name", "pass");
        Account account2 = new Account("name2", "pass2");
        Account account3 = new Account("name3", "pass3");
        assertEquals(Set.of(account, account2, account3), loader.loadAccounts(),
            "LoadAccounts should read accounts correctly!");
    }

}
