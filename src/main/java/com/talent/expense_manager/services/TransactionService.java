package main.java.com.talent.expense_manager.services;

import main.java.com.talent.expense_manager.model.Transaction;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TransactionService {

    private final Map<String, Transaction> transactions = new HashMap<>();

    public void addTransaction(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
    }

    public Transaction getActiveTransaction(String id) {
        Transaction transaction = transactions.get(id);

        if (transaction == null || !transaction.isActive()) {
            throw new IllegalStateException("Transaction not found or inactive");
        }
        return transaction;
    }

    public List<Transaction> getAllActiveTransactions() {
        return transactions.values()
                .stream()
                .filter(Transaction::isActive)
                .collect(Collectors.toList());
    }

    public List<Transaction> findBy(Predicate<Transaction> condition) {
        return transactions.values()
                .stream()
                .filter(Transaction::isActive)
                .filter(condition)
                .collect(Collectors.toList());
    }

    public void deleteTransaction(String id) {
        Transaction transaction = getActiveTransaction(id);
        transaction.deactivate();
    }

    public List<Transaction> getTransactionsByDateRange(LocalDateTime start, LocalDateTime end) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions.values()) {
            if (t.isActive() && !t.getCreatedAt().isBefore(start) && !t.getCreatedAt().isAfter(end)) {
                result.add(t);
            }
        }
        return result;
    }
}