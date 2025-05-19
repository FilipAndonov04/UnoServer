package bg.sofia.uni.fmi.mjt.uno.user.command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RefreshCommandTest {

    @Test
    void testExecute() {
        RefreshCommand refreshCommand = new RefreshCommand();
        assertDoesNotThrow(refreshCommand::execute);
    }
}
