package main.java.com.talent.expense_manager.customexception;

public class InsufficientBalanceException extends ExpenseManagerException {
    public InsufficientBalanceException(String msg) {
        super(msg);
    }
}