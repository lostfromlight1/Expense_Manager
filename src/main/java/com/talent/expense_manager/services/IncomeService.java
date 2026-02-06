package main.java.com.talent.expense_manager.services;

import main.java.com.talent.expense_manager.model.Income;
import main.java.com.talent.expense_manager.model.Transaction;
import main.java.com.talent.expense_manager.model.enum_type.IncomeType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IncomeService {
   private final TransactionService transactionService;
   private final WalletService walletService;

    public IncomeService(TransactionService transactionService, WalletService walletService) {
        this.transactionService = transactionService;
        this.walletService = walletService;
    }

    public Income addIncome(String walletId, double amount, IncomeType type){
        if (amount <= 0){
            throw new IllegalArgumentException("Income must be positive");
        }

        String id = "INC-"+ UUID.randomUUID().toString().substring(0,5);
        Income income = new Income(id , walletId , amount , type);

        transactionService.addTransaction(income);

        return income;
    }

    public void updateIncomeAmount(String incomeId, double newAmount){
        if(newAmount <= 0){
            throw new IllegalArgumentException("Income must be positive");
        }
        Transaction transaction = transactionService.getActiveTransaction(incomeId);
        transaction.updateAmount(newAmount);
    }

    public  void  deleteIncome(String incomeId){
        transactionService.deleteTransaction(incomeId);
    }

    public List<Income> getAllIncome(String walletId){
        List<Transaction> allTransaction = transactionService.getAllActiveTransactions();

        List<Income> finalIncomeList = new ArrayList<>();

        for(Transaction transaction : allTransaction){
            if (transaction instanceof Income incomeTransaction){
                if(transaction.getWalletId().equals(walletId)){
                    finalIncomeList.add(incomeTransaction);
                }
            }
        }

        return finalIncomeList;
    }

    public double calculateWalletBalance(String walletId){
        List<Transaction>  walletTransactions = new ArrayList<>();

        for (Transaction transaction : transactionService.getAllActiveTransactions()){
            if(transaction.getWalletId().equals(walletId)){
                walletTransactions.add(transaction);
            }
        }

        return walletService.recalculateBalance(walletTransactions);
    }
}
