package bg.sofia.uni.fmi.mjt.uno.database.game.account;

import bg.sofia.uni.fmi.mjt.uno.database.account.Account;
import bg.sofia.uni.fmi.mjt.uno.database.game.account.data.AccountData;

public interface AccountDataDatabase {

    void add(Account account, AccountData accountData);

    boolean contains(Account account);

    AccountData get(Account account);

}
