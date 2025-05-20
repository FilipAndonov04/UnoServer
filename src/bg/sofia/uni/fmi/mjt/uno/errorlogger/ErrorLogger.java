package bg.sofia.uni.fmi.mjt.uno.errorlogger;

public interface ErrorLogger {

    /**
     * Logs an error
     *
     * @param exception The error to be logged
     */
    void log(Exception exception);

}
