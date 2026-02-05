package main.java.com.talent.expense_manager.customexception;

public class BudgetExceededException extends ExpenseManagerException {
    public BudgetExceededException(String msg) {
        super(msg);
    }
}