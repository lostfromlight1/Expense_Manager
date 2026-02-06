package main.java.com.talent.expense_manager.model;

import java.time.LocalDateTime;

public abstract class Transaction {

    protected final String id;
    protected final String walletId;

    protected double amount;

    protected boolean isActive;

    protected final LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected LocalDateTime deletedAt;

    protected Transaction(String id, String walletId, double amount) {
        validateAmount(amount);

        this.id = id;
        this.walletId = walletId;
        this.amount = amount;

        this.isActive = true;

        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.deletedAt = null;
    }

    public abstract double getSignedAmount();

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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void updateAmount(double newAmount) {
        validateAmount(newAmount);
        this.amount = newAmount;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.isActive = false;
        this.deletedAt = LocalDateTime.now();
        this.updatedAt = this.deletedAt;
    }

    protected void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive");
        }
    }

    public abstract String getTransactionType();
}
