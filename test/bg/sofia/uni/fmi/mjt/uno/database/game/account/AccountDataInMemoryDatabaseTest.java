package bg.sofia.uni.fmi.mjt.uno.database.game.account;

import bg.sofia.uni.fmi.mjt.uno.database.account.Account;
import bg.sofia.uni.fmi.mjt.uno.database.game.account.data.AccountData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountDataInMemoryDatabaseTest {

    private AccountDataInMemoryDatabase database;
    private Account mockAccount;
    private AccountData mockAccountData;

    @BeforeEach
    void setUp() {
        database = new AccountDataInMemoryDatabase();
        mockAccount = mock(Account.class);
        mockAccountData = mock(AccountData.class);
    }

    @Test
    void testAddAndContainsAccount() {
        database.add(mockAccount, mockAccountData);
        assertTrue(database.contains(mockAccount), "Database should contain the added account");
    }

    @Test
    void testAddDoesNotOverrideExistingAccount() {
        AccountData anotherMockData = mock(AccountData.class);
        database.add(mockAccount, mockAccountData);
        database.add(mockAccount, anotherMockData);

        assertEquals(mockAccountData, database.get(mockAccount), "Existing account data should not be overridden");
    }

    @Test
    void testGetReturnsCorrectAccountData() {
        database.add(mockAccount, mockAccountData);
        assertEquals(mockAccountData, database.get(mockAccount), "get() should return the correct AccountData");
    }

    @Test
    void testGetReturnsNullForNonExistentAccount() {
        assertNull(database.get(mockAccount), "get() should return null for a non-existent account");
    }

}
