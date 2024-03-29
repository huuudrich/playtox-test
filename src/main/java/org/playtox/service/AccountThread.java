package org.playtox.service;

import org.playtox.model.Account;
import org.playtox.util.RandomUtils;

import java.util.List;

public class AccountThread extends Thread {

    private final List<Account> accounts;
    private final TransferManager manager;

    public AccountThread(List<Account> accounts,
                         TransferManager manager) {
        this.accounts = accounts;
        this.manager = manager;
    }

    public void run() {
        await();

        int oneInd = RandomUtils.randomInt(0, accounts.size());
        int twoInd = RandomUtils.randomIntWithExclude(0, accounts.size(), oneInd);

        manager.moneyTransfer(accounts.get(oneInd), accounts.get(twoInd));
    }

    private void await() {
        try {
            Thread.sleep(RandomUtils.randomInt(1000, 2000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
