package bg.sofia.uni.fmi.mjt.uno.database.account;

import bg.sofia.uni.fmi.mjt.uno.database.account.exception.UsernameTakenException;
import bg.sofia.uni.fmi.mjt.uno.database.account.saver.DatabaseSaver;
import bg.sofia.uni.fmi.mjt.uno.database.account.saver.exception.DatabaseSavingException;

import java.util.Collections;
import java.util.Set;

public class InMemoryDatabase implements Database {

    private final Set<Account> accounts;
    private final DatabaseSaver saver;

    public InMemoryDatabase(Set<Account> accounts, DatabaseSaver saver) {
        assertNotNull(accounts, "Null accounts given!");
        assertNotNull(saver, "Null saver given!");

        this.accounts = accounts;
        this.saver = saver;
    }

    @Override
    public void addAccount(Account account) throws UsernameTakenException {
        assertNotNull(account, "Account cannot be null!");

        synchronized (this) {
            if (!isFreeUsername(account.username())) {
                throw new UsernameTakenException(
                    "There is already an account with that username in the database!");
            }

            accounts.add(account);
            try {
                saver.saveAccount(account);
            } catch (DatabaseSavingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean containsAccount(Account account) {
        assertNotNull(account, "Account cannot be null!");

        synchronized (this) {
            return accounts.contains(account);
        }
    }

    @Override
    public boolean isFreeUsername(String username) {
        assertNotNull(username, "Username cannot be null!");

        synchronized (this) {
            return accounts.stream()
                .map(Account::username)
                .noneMatch(e -> e.equals(username));
        }
    }

    @Override
    public synchronized Set<Account> getAccounts() {
        return Collections.unmodifiableSet(accounts);
    }

    private void assertNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

}
