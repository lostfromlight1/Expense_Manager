package main.java.com.talent.expense_manager;

import main.java.com.talent.expense_manager.model.*;
import main.java.com.talent.expense_manager.model.enum_type.*;
import main.java.com.talent.expense_manager.services.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import main.java.com.talent.expense_manager.model.*;
import main.java.com.talent.expense_manager.model.enum_type.*;
import main.java.com.talent.expense_manager.services.*;
import main.java.com.talent.expense_manager.customexception.*;


public class ExpenseManagerApplication {
    private static final AccountService accountService = new AccountService();
    private static final TransactionService transactionService = new TransactionService();
    private static final WalletService walletService = new WalletService();
    private static final ExpenseService expenseService = new ExpenseService(transactionService, walletService);
    private static final IncomeService incomeService = new IncomeService(transactionService, walletService);

    // Transaction History Log
    private static final List<String> actionLog = new ArrayList<>();

    private static Account currentAccount = null;
    private static MyWallet currentWallet = null;
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        System.out.println("=== EXPENSE MANAGER SYSTEM ===");

        login();
        setupWallet();

        boolean exit = false;
        while (!exit) {
            printMenu();
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1.1" -> addExpense();
                    case "1.2" -> updateExpense();
                    case "1.3" -> deleteExpense();
                    case "2.1" -> addIncome();
                    case "2.2" -> updateIncome();
                    case "2.3" -> deleteIncome();
                    case "3" -> viewTransactionsByType();
                    case "4" -> viewByFilter();
                    case "5" -> viewMonthlySummary();
                    case "6" -> checkBudget();
                    case "7" -> viewActionLog(); // New Option
                    case "0" -> exit = true;
                    default -> System.out.println("Invalid option.");
                }
            } catch (ExpenseManagerException e) {
                System.err.println("APP ERROR: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println("VALIDATION ERROR: Invalid category or input format.");
            } catch (Exception e) {
                System.err.println("SYSTEM ERROR: An unexpected error occurred.");
            }
        }
    }

    private static void login() {
        while (true) {
            try {
                System.out.println("--- Login ---");
                System.out.print("Enter Name: ");
                String name = scanner.nextLine();
                if (name.isBlank()) throw new ValidationException("Name cannot be empty");

                System.out.print("Enter Email (eg: @gmail.com): ");
                String email = scanner.nextLine();
                if (!email.toLowerCase().endsWith("@gmail.com")) {
                    throw new ValidationException("Email must be a valid @gmail.com address");
                }

                currentAccount = accountService.createAccount(name, email);
                addToLog("LOGIN", "Account created for " + name);
                break;
            } catch (ValidationException e) {
                System.out.println("Login Failed: " + e.getMessage());
            }
        }
    }

    private static void setupWallet() {
        currentWallet = walletService.createWallet(currentAccount.getId());
        addToLog("WALLET", "Default wallet created: " + currentWallet.getId());
    }

    private static void printMenu() {
        double balance = incomeService.calculateWalletBalance(currentWallet.getId());
        System.out.println("\n==================================");
        System.out.println("ACCOUNT: " + currentAccount.getName() + " | BALANCE: $" + balance);
        System.out.println("==================================");
        System.out.println("1. Expense (1.1 Add / 1.2 Edit / 1.3 Delete)");
        System.out.println("2. Income  (2.1 Add / 2.2 Edit / 2.3 Delete)");
        System.out.println("3. View Transactions by Type");
        System.out.println("4. View by Date Range");
        System.out.println("5. View Monthly Summary");
        System.out.println("6. Check Budget");
        System.out.println("7. View Action Logs");
        System.out.println("0. Exit");
        System.out.print("Option: ");
    }

    // --- LOGGING ---
    private static void addToLog(String action, String details) {
        String timestamp = LocalDateTime.now().format(formatter);
        actionLog.add(String.format("[%s] %s: %s", timestamp, action, details));
    }

    private static void viewActionLog() {
        System.out.println("\n--- SYSTEM ACTION LOG ---");
        if (actionLog.isEmpty()) System.out.println("No logs available.");
        actionLog.forEach(System.out::println);
    }

    // --- EXPENSE METHODS ---
    private static void addExpense() throws ValidationException {
        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.println("Categories: FOOD, TRANSPORT, RENT, SHOPPING, MEDICINE, BILLS");
        System.out.print("Enter Category: ");
        ExpenseType type = ExpenseType.valueOf(scanner.nextLine().toUpperCase());

        Expense e = expenseService.addExpense(currentWallet.getId(), amount, type);
        addToLog("CREATE", "Expense " + e.getId() + " ($" + amount + ") Category: " + type);
    }

    private static void updateExpense() throws NotFoundException {
        System.out.print("Enter Expense ID: ");
        String id = scanner.nextLine();
        System.out.print("New Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        expenseService.updateExpenseAmount(id, amount);
        addToLog("UPDATE", "Expense " + id + " updated to $" + amount);
    }

    private static void deleteExpense() {
        System.out.print("Enter Expense ID to delete: ");
        String id = scanner.nextLine();
        expenseService.deleteExpense(id);
        addToLog("DELETE", "Expense " + id + " deactivated");
    }

    // --- INCOME METHODS ---
    private static void addIncome() {
        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.println("Categories: SALARY, BONUS, GIFT, INVESTMENT");
        System.out.print("Enter Category: ");
        IncomeType type = IncomeType.valueOf(scanner.nextLine().toUpperCase());

        Income i = incomeService.addIncome(currentWallet.getId(), amount, type);
        addToLog("CREATE", "Income " + i.getId() + " ($" + amount + ") Category: " + type);
    }

    private static void updateIncome() {
        System.out.print("Enter Income ID: ");
        String id = scanner.nextLine();
        System.out.print("New Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        incomeService.updateIncomeAmount(id, amount);
        addToLog("UPDATE", "Income " + id + " updated to $" + amount);
    }

    private static void deleteIncome() {
        System.out.print("Enter Income ID to delete: ");
        String id = scanner.nextLine();
        incomeService.deleteIncome(id);
        addToLog("DELETE", "Income " + id + " deactivated");
    }

    // --- VIEW METHODS ---
    private static void viewTransactionsByType() {
        System.out.print("Type (INCOME or EXPENSE): ");
        String input = scanner.nextLine().toUpperCase();

        if (input.equals("INCOME")) {
            List<Income> list = incomeService.getAllIncome(currentWallet.getId());
            list.forEach(i -> System.out.println(i.getId() + " | +$" + i.getAmount() + " | Category: " + i.getType() + " | " + i.getCreatedAt().format(formatter)));
        } else if (input.equals("EXPENSE")) {
            List<Expense> list = expenseService.getAllExpenses(currentWallet.getId());
            list.forEach(e -> System.out.println(e.getId() + " | -$" + e.getAmount() + " | Category: " + e.getType() + " | " + e.getCreatedAt().format(formatter)));
        }
    }

    private static void viewByFilter() {
        try {
            System.out.print("Start Date (YYYY-MM-DD): ");
            LocalDateTime start = LocalDateTime.parse(scanner.nextLine() + "T00:00:00");
            System.out.print("End Date (YYYY-MM-DD): ");
            LocalDateTime end = LocalDateTime.parse(scanner.nextLine() + "T23:59:59");

            transactionService.getTransactionsByDateRange(start, end).stream()
                    .filter(t -> t.getWalletId().equals(currentWallet.getId()))
                    .forEach(t -> System.out.println(t.getId() + " | " + t.getSignedAmount() + " | " + t.getCreatedAt().format(formatter)));
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    private static void viewMonthlySummary() {
        LocalDateTime now = LocalDateTime.now();
        double totalInc = 0;
        double totalExp = 0;

        for (Transaction t : transactionService.getAllActiveTransactions()) {
            if (t.getWalletId().equals(currentWallet.getId()) && t.getCreatedAt().getMonth() == now.getMonth()) {
                if (t instanceof Income) totalInc += t.getAmount();
                if (t instanceof Expense) totalExp += t.getAmount();
            }
        }
        System.out.println("--- Summary for " + now.getMonth() + " ---");
        System.out.println("Total Income:  $" + totalInc);
        System.out.println("Total Expense: $" + totalExp);
        System.out.println("Net Flow:      $" + (totalInc - totalExp));
    }

    private static void checkBudget() throws BudgetExceededException {
        System.out.print("Set temporary budget limit: ");
        double limit = Double.parseDouble(scanner.nextLine());
        currentWallet.setBudgetLimit(limit);

        double balance = incomeService.calculateWalletBalance(currentWallet.getId());
        if (walletService.isOverBudget(currentWallet, balance)) {
            throw new BudgetExceededException("ALERT: You have exceeded your budget of $" + limit);
        } else {
            System.out.println("Status: Within budget.");
        }
    }
}