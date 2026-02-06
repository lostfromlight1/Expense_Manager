package main.java.com.talent.expense_manager.services;

import main.java.com.talent.expense_manager.model.Account;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountService {
    private final Map<String, Account> accounts = new HashMap<>();

    public Account createAccount(String name, String email) {
        String id = "ACC-" + UUID.randomUUID().toString().substring(0, 5);
        Account account = new Account(id, name, email);
        accounts.put(id, account);
        return account;
    }

    public Account getAccount(String accountId) {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountId);
        }
        return account;
    }
}