package com.example.usecase;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import com.example.entity.TransactionFilterField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.example.entity.BankAccount;
import com.example.entity.Transaction;
import com.example.entity.TransactionType;
import com.example.usecase.dao.IBankAccountDao;
import com.example.usecase.dao.ITransactionDao;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionService implements ITransactionService {
    private final ITransactionDao transactionDao;
    private final IBankAccountDao accountDao;
    @Autowired
    public TransactionService(ITransactionDao transactionDao, IBankAccountDao accountDao) {
        this.transactionDao = transactionDao;
        this.accountDao = accountDao;
    }

    @Override
    @Transactional
    public TransactionResult Transfer(String fromAccountId, String toAccountId, Double amount) {
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
            .type(TransactionType.WITHDRAW)
            .accountId(fromAccount.getId())
            .counterPartyId(toAccount.getId())
            .createdDate(Date.valueOf(LocalDate.now()))
            .createdTime(Time.valueOf(LocalTime.now()))
            .amount(amount)
            .currency("VND")
            .status("SUCCESS")
            .build();
            transactionDao.CreateTransaction(transactionOfFromAccount);

            Transaction transactionOfToAccount = Transaction.builder()
            .type(TransactionType.DEPOSIT)
            .accountId(toAccount.getId())
            .createdDate(Date.valueOf(LocalDate.now()))
            .createdTime(Time.valueOf(LocalTime.now()))
            .amount(amount)
            .currency("VND")
            .status("SUCCESS")
            .build();
            transactionDao.CreateTransaction(transactionOfToAccount);

            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);
            accountDao.UpdateAccount(fromAccount);
            accountDao.UpdateAccount(toAccount);

            return TransactionResult.SUCCESS;
        }
        catch(Exception e){
            log.error("Error during transfer: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return TransactionResult.UNKNOWN_ERROR;
        }

    }

    @Override
    @Transactional
    public TransactionResult Deposit(String accountId, Double amount) {
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
          
            Transaction transaction = Transaction.builder()
            .type(TransactionType.DEPOSIT)
            .accountId(account.getId())
            .createdDate(Date.valueOf(LocalDate.now()))
            .createdTime(Time.valueOf(LocalTime.now()))
            .amount(amount)
            .currency("VND")
            .status("SUCCESS")
            .build();
            transactionDao.CreateTransaction(transaction);

            account.setBalance(account.getBalance() + amount);
            accountDao.UpdateAccount(account);

            return TransactionResult.SUCCESS;
        }
        catch(Exception e){
            log.error("Error during deposit: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return TransactionResult.UNKNOWN_ERROR;
        }
    }

    @Override
    @Transactional
    public TransactionResult Withdraw(String accountId, Double amount) {
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
            .accountId(account.getId())
            .createdDate(Date.valueOf(LocalDate.now()))
            .createdTime(Time.valueOf(LocalTime.now()))
            .amount(amount)
            .currency("VND")
            .status("SUCCESS")
            .build();
            transactionDao.CreateTransaction(transaction);

            account.setBalance(account.getBalance() - amount);
            accountDao.UpdateAccount(account);

            return TransactionResult.SUCCESS;
        }
        catch(Exception e){
            log.error("Error during withdraw: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return TransactionResult.UNKNOWN_ERROR;
        }
    }

    @Override
    public List<Transaction> GetTransactions(String accountId) {
        try{
            List<Transaction> transactions = transactionDao.GetTransactions(accountId);

            return transactions;
        }
        catch (Exception e){
            log.error("Error during get transactions: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public List<Transaction> GetTransactions(Map<TransactionFilterField, Object> filter) {
        try{
            List<Transaction> transactions = transactionDao.GetTransactions(filter);

            return transactions;
        }
        catch (Exception e){
            log.error("Error during get transactions: {}", e.getMessage());
            return null;
        }
    }

}
