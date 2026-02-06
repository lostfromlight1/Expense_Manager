package main.java.com.talent.expense_manager.services;

import main.java.com.talent.expense_manager.model.MyWallet;
import main.java.com.talent.expense_manager.model.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WalletService {

    private final Map<String, MyWallet> wallets = new HashMap<>();

    public MyWallet createWallet(String accountId) {
        String id = "WAL-" + UUID.randomUUID().toString().substring(0, 5);
        MyWallet wallet = new MyWallet(id, accountId);
        wallets.put(id, wallet);
        return wallet;
    }

    public MyWallet getActiveWallet(String walletId) {
        MyWallet wallet = wallets.get(walletId);
        if (wallet == null || !wallet.isActive()) {
            throw new IllegalStateException("Wallet not found or inactive");
        }
        return wallet;
    }

    public double recalculateBalance(List<Transaction> transactions) {
        double total = 0.0;

        for (Transaction t : transactions) {
            if (t.isActive()) {
                total += t.getSignedAmount();
            }
        }

        return total;
    }

    public boolean isOverBudget(MyWallet wallet, double currentBalance) {
        if (wallet.getBudgetLimit() <= 0) {
            return false;
        }

        double spendingLimit = -wallet.getBudgetLimit();

        return currentBalance < spendingLimit;
    }
}