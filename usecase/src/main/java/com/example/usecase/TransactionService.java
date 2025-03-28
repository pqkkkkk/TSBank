package com.example.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.BankAccount;
import com.example.entity.Transaction;
import com.example.entity.TransactionType;
import com.example.usecase.dao.IBankAccountDao;
import com.example.usecase.dao.ITransactionDao;

@Service
public class TransactionService implements ITransactionService {
    private final ITransactionDao transactionDao;
    private final IBankAccountDao accountDao;

    @Autowired
    public TransactionService(ITransactionDao transactionDao, IBankAccountDao accountDao) {
        this.transactionDao = transactionDao;
        this.accountDao = accountDao;
    }

    @Override
    public TransactionResult Transfer(String fromAccountId, String toAccountId, Integer amount) {
        try{
            if(amount < 0){
                return TransactionResult.INVALID_AMOUNT;
            }

            BankAccount fromAccount = accountDao.GetAccountById(fromAccountId);
            BankAccount toAccount = accountDao.GetAccountById(toAccountId);

            if(fromAccount == null){
                return TransactionResult.SOURCE_NOT_FOUND;
            }
            if(fromAccount.getIsLocked()){
                return TransactionResult.LOCKED_ACCOUNT;
            }
            if (toAccount == null){
                return TransactionResult.DESTINATION_NOT_FOUND;
            }

            if(fromAccount.getBalance() < amount){
                return TransactionResult.INSUFFICIENT_BALANCE;
            }

            Transaction transactionOfFromAccount = Transaction.builder()
            .type(TransactionType.DEPOSIT)
            .build();
            transactionDao.CreateTransaction(transactionOfFromAccount);

            Transaction transactionOfToAccount = Transaction.builder()
            .type(TransactionType.WITHDRAW)
            .build();
            transactionDao.CreateTransaction(transactionOfToAccount);

            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);
            accountDao.UpdateAccount(fromAccount);
            accountDao.UpdateAccount(toAccount);

            return TransactionResult.SUCCESS;
        }
        catch(Exception e){
            return TransactionResult.UNKNOWN_ERROR;
        }

    }

    @Override
    public TransactionResult Deposit(String accountId, Integer amount) {
        try{
            if(amount < 0){
                return TransactionResult.INVALID_AMOUNT;
            }

            BankAccount account = accountDao.GetAccountById(accountId);

            if(account == null){
                return TransactionResult.ACCOUNT_NOT_FOUND;
            }
            if(account.getIsLocked()){
                return TransactionResult.LOCKED_ACCOUNT;
            }
            if(account.getBalance() < amount){
                return TransactionResult.INSUFFICIENT_BALANCE;
            }

            Transaction transaction = Transaction.builder()
            .type(TransactionType.DEPOSIT)
            .build();
            transactionDao.CreateTransaction(transaction);

            account.setBalance(account.getBalance() + amount);
            accountDao.UpdateAccount(account);

            return TransactionResult.SUCCESS;
        }
        catch(Exception e){
            return TransactionResult.UNKNOWN_ERROR;
        }
    }

    @Override
    public TransactionResult Withdraw(String accountId, Integer amount) {
        try{
            if(amount < 0){
                return TransactionResult.INVALID_AMOUNT;
            }

            BankAccount account = accountDao.GetAccountById(accountId);

            if(account == null){
                return TransactionResult.ACCOUNT_NOT_FOUND;
            }
            if(account.getIsLocked()){
                return TransactionResult.LOCKED_ACCOUNT;
            }
            if(account.getBalance() < amount){
                return TransactionResult.INSUFFICIENT_BALANCE;
            }

            Transaction transaction = Transaction.builder()
            .type(TransactionType.WITHDRAW)
            .build();
            transactionDao.CreateTransaction(transaction);

            account.setBalance(account.getBalance() - amount);
            accountDao.UpdateAccount(account);

            return TransactionResult.SUCCESS;
        }
        catch(Exception e){
            return TransactionResult.UNKNOWN_ERROR;
        }
    }
}
