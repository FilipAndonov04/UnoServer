package bg.sofia.uni.fmi.mjt.uno.game.gamelogs.spectator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

class ConsolePrintingSpectatorTest {

    private StringWriter stringWriter;
    private PrintWriter printWriter;
    private ConsolePrintingSpectator spectator;

    @BeforeEach
    void setUp() {
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        spectator = new ConsolePrintingSpectator(printWriter);
    }

    @Test
    void testConstructorThrowsExceptionWhenPrintWriterIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ConsolePrintingSpectator(null));
        assertEquals("Print writer cannot be null!", exception.getMessage(), "Constructor should throw an exception if PrintWriter is null");
    }

    @Test
    void testOnEventPrintsEventToConsole() {
        String event = "Player joined the game";
        spectator.onEvent(event);
        printWriter.flush();
        assertEquals(event + System.lineSeparator(), stringWriter.toString(), "onEvent() should print the event to the console");
    }

    @Test
    void testOnEventThrowsExceptionWhenEventIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> spectator.onEvent(null));
        assertEquals("Event cannot be null!", exception.getMessage(), "onEvent() should throw an exception if event is null");
    }

}