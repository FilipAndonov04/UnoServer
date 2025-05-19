package bg.sofia.uni.fmi.mjt.uno.errorlogger;

public interface ErrorLogger {

    /**
     * Logs an error
     *
     * @param exception The
     */
    void log(Exception exception);

}
