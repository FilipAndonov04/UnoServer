package bg.sofia.uni.fmi.mjt.uno.errorlogger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class StackTraceInFilePrinter implements ErrorLogger {

    private final String fileName;

    public StackTraceInFilePrinter(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("File name cannot be null!");
        }
        if (fileName.isBlank()) {
            throw new IllegalArgumentException("File name cannot be blank!");
        }

        this.fileName = fileName;
    }

    @Override
    public void log(Exception exception) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileName, true), true)) {
            printWriter.println(LocalDateTime.now());
            printWriter.println(exception.getMessage());
            exception.printStackTrace(printWriter);
            printWriter.println();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
