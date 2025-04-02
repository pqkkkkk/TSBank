package com.example.usecase.dao;

import java.util.List;

import com.example.entity.BankAccount;

public interface IBankAccountDao {
    public String AddAccount(BankAccount account);
    public Integer UpdateAccount(BankAccount account);
    public BankAccount GetAccountById(String accountId);
    public List<BankAccount> GetAccountsOfCustomer(Integer customerId);
}
