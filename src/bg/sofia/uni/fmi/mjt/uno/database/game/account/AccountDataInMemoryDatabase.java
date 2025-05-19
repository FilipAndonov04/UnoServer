package bg.sofia.uni.fmi.mjt.uno.database.game.account;

import bg.sofia.uni.fmi.mjt.uno.database.account.Account;
import bg.sofia.uni.fmi.mjt.uno.database.game.account.data.AccountData;

import java.util.HashMap;
import java.util.Map;

public class AccountDataInMemoryDatabase implements AccountDataDatabase {

    private final Map<Account, AccountData> map = new HashMap<>();

    @Override
    public synchronized void add(Account account, AccountData accountData) {
        map.putIfAbsent(account, accountData);
    }

    @Override
    public synchronized boolean contains(Account account) {
        return map.containsKey(account);
    }

    @Override
    public synchronized AccountData get(Account account) {
        return map.get(account);
    }

}
