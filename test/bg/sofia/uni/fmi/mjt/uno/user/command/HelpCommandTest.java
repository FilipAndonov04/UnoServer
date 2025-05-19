package bg.sofia.uni.fmi.mjt.uno.user.command;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

public class HelpCommandTest {

    private PrintWriter out;
    private HelpCommand helpCommand;

    @BeforeEach
    void setUp() {
        out = mock(PrintWriter.class);
        helpCommand = new HelpCommand(out);
    }

    @Test
    void testCommandText() {
        assertEquals("help", HelpCommand.COMMAND_TEXT);
    }

    @Test
    void testExecutePrintsCorrectOutput() {
        assertDoesNotThrow(() -> helpCommand.execute(), "Should not throw");
    }

    @Test
    void testCommandOf() {
        Command command = HelpCommand.of(out);

        assertNotNull(command);
        assertInstanceOf(HelpCommand.class, command);
    }
}
