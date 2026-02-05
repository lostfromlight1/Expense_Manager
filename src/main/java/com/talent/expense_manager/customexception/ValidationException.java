package main.java.com.talent.expense_manager.customexception;

public class ValidationException extends ExpenseManagerException {
    public ValidationException(String msg) {
        super(msg);
    }
}