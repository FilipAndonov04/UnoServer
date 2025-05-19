package bg.sofia.uni.fmi.mjt.uno.user.exception;

public class InvalidUserOperation extends Exception {

    public InvalidUserOperation(String message) {
        super(message);
    }

    public InvalidUserOperation(String message, Throwable cause) {
        super(message, cause);
    }

}
