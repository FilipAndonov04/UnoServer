package bg.sofia.uni.fmi.mjt.uno.errorlogger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StackTraceInFilePrinterTest {

    @Test
    void testConstructorThrowsWithNullFileName() {
        assertThrows(IllegalArgumentException.class, () -> new StackTraceInFilePrinter(null),
            "Constructor should throw then given null name!");
    }

    @Test
    void testConstructorThrowsWithBlankFileName() {
        assertThrows(IllegalArgumentException.class, () -> new StackTraceInFilePrinter(""),
            "Constructor should throw then given blank name!");
    }

    @Test
    void testConstructorThrowsValidFileName() {
        assertDoesNotThrow(() -> new StackTraceInFilePrinter("FileName.txt"),
            "Constructor should not throw then given valid name!");
    }

}
