package main.java.com.talent.expense_manager.model;

import main.java.com.talent.expense_manager.model.enum_type.IncomeType;

public class Income extends Transaction {

    private final IncomeType type;

    public Income(String id, String walletId, double amount, IncomeType type) {
        super(id, walletId, amount);
        this.type = type;
    }

    public IncomeType getType() {
        return type;
    }

    @Override
    public String getTransactionType() {
        return "INCOME";
    }

    @Override
    public double getSignedAmount() {
        return amount;
    }
}
