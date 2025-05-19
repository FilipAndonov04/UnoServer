package bg.sofia.uni.fmi.mjt.uno.database.account.loader;

import bg.sofia.uni.fmi.mjt.uno.database.account.Account;
import bg.sofia.uni.fmi.mjt.uno.database.account.cipher.StringCipher;
import bg.sofia.uni.fmi.mjt.uno.database.account.loader.exception.DatabaseLoadingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;
import java.util.stream.Collectors;

public class FromStreamLoader implements DatabaseLoader {

    private static final String ERROR_MESSAGE = "Error occurred during loading the accounts!";

    private static final char EQUALS_SIGN = '=';
    private static final char DELIMITER_SIGN = ' ';
    private static final int COUNT_OF_WORDS_IN_LINE = 2;

    private final StringCipher cipher;
    private final Reader reader;

    public FromStreamLoader(Reader reader, StringCipher cipher) {
        assertNotNull(reader, "Reader cannot be null!");
        assertNotNull(cipher, "Cipher cannot be null!");

        this.reader = reader;
        this.cipher = cipher;
    }

    @Override
    public Set<Account> loadAccounts() throws DatabaseLoadingException {
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            return bufferedReader.lines()
                .map(this::getAccountFromLine)
                .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new DatabaseLoadingException(
                ERROR_MESSAGE + System.lineSeparator() + "Error occurred while reading from the stream!");
        } catch (Exception e) {
            throw new DatabaseLoadingException(ERROR_MESSAGE + System.lineSeparator() + e.getMessage(), e);
        }
    }

    private Account getAccountFromLine(String line) {
        int delimiterIndex = line.indexOf(DELIMITER_SIGN);
        String username = line.substring(line.indexOf(EQUALS_SIGN) + 1, delimiterIndex);
        String password = cipher.decrypt(line.substring(line.indexOf(EQUALS_SIGN, delimiterIndex) + 1));
        return new Account(username, password);
    }

    private void assertNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

}
