package org.playtox;

import org.playtox.model.Account;
import org.playtox.service.AccountThread;
import org.playtox.service.TransferManager;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int COUNT_OF_ACCOUNTS = 4;
    private static final int COUNT_OF_THREADS = 100;

    public static void main(String[] args) {
        List<Account> accounts = new ArrayList<>();
        TransferManager manager = new TransferManager();

        for (int i = 0; i < COUNT_OF_ACCOUNTS; i++) {
            accounts.add(Account.of(String.valueOf(i), 10000));
        }

        for (int i = 0; i < COUNT_OF_THREADS; i++) {
            AccountThread thread = new AccountThread(accounts, manager);
            thread.start();
        }
    }
}
