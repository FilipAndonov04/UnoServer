package bg.sofia.uni.fmi.mjt.uno.database.account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {

    @Test
    void testAccountConstructorWithNullUsername() {
        assertThrows(IllegalArgumentException.class, () -> new Account(null, "pass"),
            "Account constructor should throw IllegalArgumentException when given a null username!");
    }

    @Test
    void testAccountConstructorWithBlankUsername() {
        assertThrows(IllegalArgumentException.class, () -> new Account("", "pass"),
            "Account constructor should throw IllegalArgumentException when given a blank username!");
    }

    @Test
    void testAccountConstructorWithNullPassword() {
        assertThrows(IllegalArgumentException.class, () -> new Account("name", null),
            "Account constructor should throw IllegalArgumentException when given a null password!");
    }

    @Test
    void testAccountConstructorWithBlankPassword() {
        assertThrows(IllegalArgumentException.class, () -> new Account("name", ""),
            "Account constructor should throw IllegalArgumentException when given a blank password!");
    }

    @Test
    void testAccountConstructorWithValidUsernameAndPassword() {
        assertDoesNotThrow(() -> new Account("name", "pass"),
            "Account constructor should not throw when given a valid username and a valid password!");
    }

}
