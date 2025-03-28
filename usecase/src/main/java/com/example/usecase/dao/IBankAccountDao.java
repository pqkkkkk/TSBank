package com.example.usecase.dao;

import com.example.entity.BankAccount;

public interface IBankAccountDao {
    public boolean AddAccount(BankAccount account);
    public boolean UpdateAccount(BankAccount account);
    public BankAccount GetAccountById(String accountId);
}
