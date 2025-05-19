package bg.sofia.uni.fmi.mjt.uno.database.account;

import bg.sofia.uni.fmi.mjt.uno.database.account.exception.UsernameTakenException;
import bg.sofia.uni.fmi.mjt.uno.database.account.saver.DatabaseSaver;
import bg.sofia.uni.fmi.mjt.uno.database.account.saver.exception.DatabaseSavingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryDatabaseTest {

    private static DatabaseSaver databaseSaver;

    @BeforeAll
    static void setUpTestCase() {
        databaseSaver = Mockito.mock(DatabaseSaver.class);
    }

    @Test
    void testInMemoryDatabaseConstructorWithNullAccounts() {
        assertThrows(IllegalArgumentException.class, () -> new InMemoryDatabase(null, databaseSaver),
            "InMemoryDatabase's constructor should throw IllegalArgumentException " +
                "when called with null set of accounts!");
    }

    @Test
    void testInMemoryDatabaseConstructorWithNullDatabaseSaver() {
        assertThrows(IllegalArgumentException.class, () -> new InMemoryDatabase(new HashSet<>(), null),
            "InMemoryDatabase's constructor should throw IllegalArgumentException " +
                "when called with null DatabaseSaver!");
    }

    @Test
    void testAddAccountWithNullAccount() {
        InMemoryDatabase database = new InMemoryDatabase(new HashSet<>(), databaseSaver);

        assertThrows(IllegalArgumentException.class, () -> database.addAccount(null),
            "Add account should throw IllegalArgumentException when called with null account!");
    }

    @Test
    void testAddAccountWithEmptyDatabase() throws UsernameTakenException, DatabaseSavingException {
        InMemoryDatabase database = new InMemoryDatabase(new HashSet<>(), databaseSaver);
        Account account = new Account("user", "pass");
        database.addAccount(account);

        assertEquals(Set.of(account), database.getAccounts(),
            "Add account should add the account to the database!");
    }

    @Test
    void testAddAccountWithUniqueAccountWithNotEmptyDatabase() throws UsernameTakenException, DatabaseSavingException {
        Account account1 = new Account("user1", "abc");
        Account account2 = new Account("user2", "abc");

        Set<Account> hashSet = new HashSet<>();
        hashSet.add(account1);
        hashSet.add(account2);

        InMemoryDatabase database = new InMemoryDatabase(hashSet, databaseSaver);
        Account account = new Account("user", "pass");
        database.addAccount(account);

        assertEquals(Set.of(account, account1, account2), database.getAccounts(),
            "Add account should add the account to the database!");
    }

    @Test
    void testAddAccountWithNotUniqueAccountWithNotEmptyDatabase() {
        Account account1 = new Account("user1", "abc");
        Account account2 = new Account("user2", "abc");

        InMemoryDatabase database = new InMemoryDatabase(Set.of(account1, account2), databaseSaver);
        Account account = new Account("user2", "pass");

        assertThrows(UsernameTakenException.class, () -> database.addAccount(account),
            "Add account should throw UsernameTakenException when trying to " +
                "add an account which username is already in the database!");
    }

    @Test
    void testContainsAccountWithNullAccount() {
        InMemoryDatabase database = new InMemoryDatabase(new HashSet<>(), databaseSaver);

        assertThrows(IllegalArgumentException.class, () -> database.containsAccount(null),
            "Contains account should throw IllegalArgumentException when called with null account!");
    }

    @Test
    void testContainsAccountWithEmptyDatabase() {
        InMemoryDatabase database = new InMemoryDatabase(new HashSet<>(), databaseSaver);
        Account account = new Account("user", "pass");

        assertFalse(database.containsAccount(account),
            "Contains account should return false when the database is empty!");
    }

    @Test
    void testContainsAccountWithAccountWithUniqueUsername() {
        Account account1 = new Account("user1", "abc");
        Account account2 = new Account("user2", "abc");

        InMemoryDatabase database = new InMemoryDatabase(Set.of(account1, account2), databaseSaver);
        Account account = new Account("user", "pass");

        assertFalse(database.containsAccount(account),
            "Contains account should return false when there are no accounts " +
                "in the database with the same username!");
    }

    @Test
    void testContainsAccountWithAccountWithSameUsernameButDifferentPassword() {
        Account account1 = new Account("user1", "abc");
        Account account2 = new Account("user2", "abc");

        InMemoryDatabase database = new InMemoryDatabase(Set.of(account1, account2), databaseSaver);
        Account account = new Account("user2", "pass");

        assertFalse(database.containsAccount(account),
            "Contains account should return false when the username matches but the password does not!");
    }

    @Test
    void testContainsAccountWithAccountWithSameUsernameAndSamePassword() {
        Account account1 = new Account("user1", "abc");
        Account account2 = new Account("user2", "abc");

        InMemoryDatabase database = new InMemoryDatabase(Set.of(account1, account2), databaseSaver);
        Account account = new Account("user2", "abc");

        assertTrue(database.containsAccount(account),
            "Contains account should return true when there is " +
                "an account in the database with the same username and password!");
    }

    @Test
    void testIsFreeUsernameWithNullUsername() {
        InMemoryDatabase database = new InMemoryDatabase(new HashSet<>(), databaseSaver);

        assertThrows(IllegalArgumentException.class, () -> database.isFreeUsername(null),
            "Is free username should throw IllegalArgumentException when called with null username!");
    }

    @Test
    void testIsFreeUsernameEmptyDatabase() {
        InMemoryDatabase database = new InMemoryDatabase(new HashSet<>(), databaseSaver);

        assertTrue(database.isFreeUsername("name"),
            "Is free username should return true when the database is empty!");
    }

    @Test
    void testIsFreeUsernameUsernamePresentInTheDatabase() {
        Account account = new Account("name", "abc");
        Set<Account> hashSet = new HashSet<>();
        hashSet.add(account);

        InMemoryDatabase database = new InMemoryDatabase(hashSet, databaseSaver);

        assertFalse(database.isFreeUsername("name"),
            "Is free username should return false when there is an account with that username in the database!");
    }

    @Test
    void testIsFreeUsernameUsernameNotPresentInTheDatabase() {
        Account account = new Account("user", "abc");
        Set<Account> hashSet = new HashSet<>();
        hashSet.add(account);

        InMemoryDatabase database = new InMemoryDatabase(hashSet, databaseSaver);

        assertTrue(database.isFreeUsername("name"),
            "Is free username should return true when there is no account with that username in the database!");
    }

}
