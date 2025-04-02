package com.example.usecase;

import java.util.List;

import com.example.entity.BankAccount;
import com.example.entity.Customer;

public interface IBankAccountService {
    public String CreateAccount(Customer customer, BankAccount account);
    public boolean LinkAccount();
    public List<BankAccount> GetAccountsOfCustomer(Integer customerId);
}
