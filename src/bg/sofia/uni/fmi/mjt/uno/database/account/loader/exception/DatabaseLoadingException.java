package bg.sofia.uni.fmi.mjt.uno.database.account.loader.exception;

public class DatabaseLoadingException extends Exception {

    public DatabaseLoadingException(String message) {
        super(message);
    }

    public DatabaseLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

}
