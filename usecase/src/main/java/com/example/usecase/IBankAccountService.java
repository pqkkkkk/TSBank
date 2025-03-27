package com.example.usecase;

import com.example.entity.BankAccount;

public interface IBankAccountService {
    public boolean CreateAccount(BankAccount account);
    public boolean LinkAccount();
}
