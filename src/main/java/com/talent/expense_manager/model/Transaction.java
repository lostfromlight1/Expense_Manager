package main.java.com.talent.expense_manager.model;

import java.time.LocalDateTime;

public abstract class Transaction {
    protected String id;
    protected String walletId;
    protected double amount;
    protected boolean isActive;
    protected LocalDateTime createdAt;

    public Transaction(String id, String walletId, double amount) {
        this.id = id;
        this.walletId = walletId;
        this.amount = amount;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getWalletId() {
        return walletId;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isActive() {
        return isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void deactivate() {
        isActive = false;
    }
}
