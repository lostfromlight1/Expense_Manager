package main.java.com.talent.expense_manager.model;

import main.java.com.talent.expense_manager.model.enum_type.ExpenseType;

public class Expense extends Transaction {

    private final ExpenseType type;

    public Expense(String id, String walletId, double amount, ExpenseType type) {
        super(id, walletId, amount);
        this.type = type;
    }

    public ExpenseType getType() {
        return type;
    }

    @Override
    public String getTransactionType() {
        return "EXPENSE";
    }

    @Override
    public double getSignedAmount() {
        return -amount; // expense subtracts
    }
}
