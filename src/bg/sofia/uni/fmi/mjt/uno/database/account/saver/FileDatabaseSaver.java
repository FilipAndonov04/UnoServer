package bg.sofia.uni.fmi.mjt.uno.database.account.saver;

import bg.sofia.uni.fmi.mjt.uno.database.account.Account;
import bg.sofia.uni.fmi.mjt.uno.database.account.cipher.StringCipher;
import bg.sofia.uni.fmi.mjt.uno.database.account.saver.exception.DatabaseSavingException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileDatabaseSaver implements DatabaseSaver {

    private static final String ERROR_MESSAGE = "Error occurred during saving an account!";

    private static final String USERNAME_TEXT = "username";
    private static final String PASSWORD_TEXT = "password";
    private static final char EQUALS_SIGN = '=';
    private static final char DELIMITER_SIGN = ' ';

    private final StringCipher cipher;
    private final String fileName;

    public FileDatabaseSaver(String fileName, StringCipher cipher) {
        assertNotNull(fileName, "File name cannot be null!");
        assertNotNull(cipher, "Cipher cannot be null!");
        if (fileName.isBlank()) {
            throw new IllegalArgumentException("File name cannot be blank!");
        }

        this.fileName = fileName;
        this.cipher = cipher;
    }

    @Override
    public void saveAccount(Account account) throws DatabaseSavingException {
        assertNotNull(account, "Account cannot be null!");

        try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileName, true), true)) {
            printWriter.println(getStringFromAccount(account));
        } catch (IOException e) {
            throw new DatabaseSavingException(
                ERROR_MESSAGE + System.lineSeparator() + "A problem occurred while writing to a file!", e);
        } catch (Exception e) {
            throw new DatabaseSavingException(ERROR_MESSAGE + System.lineSeparator() + "Unexpected error occurred!");
        }
    }

    private String getStringFromAccount(Account account) {
        return USERNAME_TEXT + EQUALS_SIGN + account.username() + DELIMITER_SIGN +
            PASSWORD_TEXT + EQUALS_SIGN + cipher.encrypt(account.password());
    }

    private void assertNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

}
