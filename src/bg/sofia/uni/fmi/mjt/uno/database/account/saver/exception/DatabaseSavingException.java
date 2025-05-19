package bg.sofia.uni.fmi.mjt.uno.database.account.saver.exception;

public class DatabaseSavingException extends Exception {

    public DatabaseSavingException(String message) {
        super(message);
    }

    public DatabaseSavingException(String message, Throwable cause) {
        super(message, cause);
    }

}
