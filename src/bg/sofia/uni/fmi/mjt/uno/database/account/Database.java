package bg.sofia.uni.fmi.mjt.uno.database.account;

import bg.sofia.uni.fmi.mjt.uno.database.account.exception.UsernameTakenException;

import java.util.Set;

public interface Database {

    /**
     * Adds account to the database
     *
     * @param account The account to be added
     * @throws UsernameTakenException If there is an account with the same username in the database
     * @throws IllegalArgumentException If the account is null
     */
    void addAccount(Account account) throws UsernameTakenException;

    /**
     * Checks whether an account is in the database
     *
     * @param account The account to be checked
     * @return true is the account is in the database, false otherwise
     * @throws IllegalArgumentException If the account is null
     */
    boolean containsAccount(Account account);

    /**
     * Checks whether an account with that username is in the database
     *
     * @param username The username to be checked
     * @return true if the username is not in the database, false otherwise
     * @throws IllegalArgumentException If the username is null
     */
    boolean isFreeUsername(String username);

    /**
     * Returns all the accounts in the database
     *
     * @return A view of the set with the accounts
     */
    Set<Account> getAccounts();

}
