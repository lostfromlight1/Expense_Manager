package main.java.com.talent.expense_manager.services;

import main.java.com.talent.expense_manager.model.Expense;
import main.java.com.talent.expense_manager.model.Transaction;
import main.java.com.talent.expense_manager.model.enum_type.ExpenseType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExpenseService {
    private final TransactionService transactionService;
    private final WalletService walletService;

    public ExpenseService(TransactionService transactionService, WalletService walletService) {
        this.transactionService = transactionService;
        this.walletService = walletService;
    }

    public Expense addExpense(String walletId, double amount, ExpenseType type) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Expense amount must be positive");
        }
        String id = "EXP-" + UUID.randomUUID().toString().substring(0, 5);
        Expense expense = new Expense(id, walletId, amount, type);

        transactionService.addTransaction(expense);
        return expense;
    }

    public void updateExpenseAmount(String expenseId, double newAmount) {
        if (newAmount <= 0) {
            throw new IllegalArgumentException("Expense amount must be positive");
        }
        Transaction transaction = transactionService.getActiveTransaction(expenseId);

        if (transaction != null) {
            transaction.updateAmount(newAmount);
        }
    }

    public void deleteExpense(String expenseId) {
        transactionService.deleteTransaction(expenseId);
    }

    public List<Expense> getAllExpenses(String walletId) {
        List<Transaction> allTransactions = transactionService.getAllActiveTransactions();
        List<Expense> finalExpenseList = new ArrayList<>();

        for (Transaction transaction : allTransactions) {
            if (transaction instanceof Expense expense && transaction.getWalletId().equals(walletId)) {
                finalExpenseList.add(expense);
            }
        }
        return finalExpenseList;
    }

    public boolean isWalletOverBudget(String walletId, double budgetLimit) {
        List<Transaction> walletTransactions = new ArrayList<>();
        List<Transaction> allTransactions = transactionService.getAllActiveTransactions();

        for (Transaction transaction : allTransactions) {
            if (transaction.getWalletId().equals(walletId)) {
                walletTransactions.add(transaction);
            }
        }

        double balance = walletService.recalculateBalance(walletTransactions);

        return balance < -budgetLimit;
    }
}