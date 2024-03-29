package org.playtox.model;

import java.util.concurrent.locks.ReentrantLock;

public class Account {

    private final String id;
    private int money;
    private final ReentrantLock lock = new ReentrantLock();

    public Account(String id,
                   int money) {
        this.id = id;
        this.money = money;
    }

    public void withdraw(int amount) {
        money -= amount;
    }

    public void deposit(int amount) {
        money += amount;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public int getMoney() {
        return money;
    }

    public static Account of(String id,
                             int money) {
        return new Account(id, money);
    }

    @Override
    public String toString() {
        return "Account{" +
               "id='" + id + '\'' +
               ", money=" + money +
               '}';
    }
}
