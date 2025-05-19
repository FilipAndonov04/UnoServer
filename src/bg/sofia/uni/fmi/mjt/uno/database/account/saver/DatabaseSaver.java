package bg.sofia.uni.fmi.mjt.uno.database.account.saver;

import bg.sofia.uni.fmi.mjt.uno.database.account.Account;
import bg.sofia.uni.fmi.mjt.uno.database.account.saver.exception.DatabaseSavingException;

public interface DatabaseSaver {

    /**
     * Saves an account
     *
     * @param account The account to be saved
     * @throws DatabaseSavingException If an error occurred during the saving
     * @throws IllegalArgumentException If the account is null
     */
    void saveAccount(Account account) throws DatabaseSavingException;

}
