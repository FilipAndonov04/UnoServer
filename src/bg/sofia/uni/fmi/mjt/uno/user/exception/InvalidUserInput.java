package bg.sofia.uni.fmi.mjt.uno.user.exception;

public class InvalidUserInput extends Exception {

    public InvalidUserInput(String message) {
        super(message);
    }

    public InvalidUserInput(String message, Throwable cause) {
        super(message, cause);
    }

}
