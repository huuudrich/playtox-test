package org.playtox.service;

import org.apache.log4j.Logger;
import org.playtox.model.Account;
import org.playtox.util.RandomUtils;

public class TransferManager {

    private static final Logger logger = Logger.getLogger(TransferManager.class);

    public void moneyTransfer(Account transferable,
                              Account receivable) {
        while (true) {
            try {
                if (attemptTransfer(transferable, receivable)) {
                    logger.info("Транзакция успешна");
                    break;
                }
            } catch (InterruptedException e) {
                logger.error("Не удалось выполнить транзакцию");
                Thread.currentThread().interrupt();
                break;
            } finally {
                unlockAccounts(transferable, receivable);
            }

            sleepRandomly();
        }
    }

    private boolean attemptTransfer(Account transferable,
                                    Account receivable) throws InterruptedException {
        if (transferable.getMoney() == 0) {
            logger.info("Баланс пуст");
            return true;
        }

        boolean gotFirstLock = transferable.getLock().tryLock();
        boolean gotSecondLock = receivable.getLock().tryLock();

        if (gotFirstLock && gotSecondLock) {
            int amount = RandomUtils.randomInt(0, transferable.getMoney());
            transferable.withdraw(amount);
            receivable.deposit(amount);
            logger.info(String.format("Транзакция: %s из %s к %s", amount, transferable, receivable));
            return true;
        }

        return false;
    }

    private void unlockAccounts(Account transferable,
                                Account receivable) {
        if (transferable.getLock().isHeldByCurrentThread()) {
            transferable.getLock().unlock();
        }
        if (receivable.getLock().isHeldByCurrentThread()) {
            receivable.getLock().unlock();
        }
    }

    private void sleepRandomly() {
        try {
            int sleepTime = RandomUtils.randomInt(1, 10);
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            logger.error("Sleep interrupted", e);
            Thread.currentThread().interrupt();
        }
    }
}
