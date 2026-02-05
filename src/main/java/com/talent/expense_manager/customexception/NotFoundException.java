package main.java.com.talent.expense_manager.customexception;

public class NotFoundException extends ExpenseManagerException {
    public NotFoundException(String msg) {
        super(msg);
    }
}