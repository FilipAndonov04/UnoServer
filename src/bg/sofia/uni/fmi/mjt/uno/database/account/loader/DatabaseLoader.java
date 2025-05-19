package bg.sofia.uni.fmi.mjt.uno.database.account.loader;

import bg.sofia.uni.fmi.mjt.uno.database.account.Account;
import bg.sofia.uni.fmi.mjt.uno.database.account.loader.exception.DatabaseLoadingException;

import java.util.Set;

public interface DatabaseLoader {

    /**
     * Loads the accounts
     *
     * @return The loaded database
     * @throws DatabaseLoadingException If an error occurs while loading the database
     */
    Set<Account> loadAccounts() throws DatabaseLoadingException;

}
