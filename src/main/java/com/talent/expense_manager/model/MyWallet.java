package main.java.com.talent.expense_manager.model;

import java.time.LocalDateTime;

public class MyWallet {

    private final String id;
    private final String accountId;

    private double balance;
    private double budgetLimit;

    private boolean isActive;

    private final LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime lastBalanceUpdatedAt;

    public MyWallet(String id, String accountId) {
        this.id = id;
        this.accountId = accountId;

        this.balance = 0;
        this.budgetLimit = 0;
        this.isActive = true;

        this.createdAt = LocalDateTime.now();
        this.lastBalanceUpdatedAt = this.createdAt;
        this.deletedAt = null;
    }

    public String getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public double getBudgetLimit() {
        return budgetLimit;
    }

    public boolean isActive() {
        return isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public LocalDateTime getLastBalanceUpdatedAt() {
        return lastBalanceUpdatedAt;
    }

    public void addBalance(double amount) {
        this.balance += amount;
        this.lastBalanceUpdatedAt = LocalDateTime.now();
    }

    public void reduceBalance(double amount) {
        this.balance -= amount;
        this.lastBalanceUpdatedAt = LocalDateTime.now();
    }

    public void setBudgetLimit(double limit) {
        this.budgetLimit = limit;
    }

    public void deactivate() {
        this.isActive = false;
        this.deletedAt = LocalDateTime.now();
    }
}
