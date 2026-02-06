package main.java.com.talent.expense_manager.model;

import java.time.LocalDateTime;

public class Account {
    private final String id;
    private String name;
    private final String userId;
    private double balance;

    public Account(String id, String name, String userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.balance = 0.0; // Start at zero
        LocalDateTime createdAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getUserId() { return userId; }
    public double getBalance() { return balance; }

    public void setName(String name) { this.name = name; }
    public void setBalance(double balance) { this.balance = balance; }
}