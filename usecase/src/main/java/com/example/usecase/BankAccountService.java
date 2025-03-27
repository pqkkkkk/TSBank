package com.example.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.BankAccount;
import com.example.usecase.dao.IBankAccountDao;

@Service
public class BankAccountService implements IBankAccountService {
    private IBankAccountDao accountDao;

    @Autowired
    public BankAccountService(IBankAccountDao accountDao) {
        this.accountDao = accountDao;
    }
    @Override
    public boolean CreateAccount(BankAccount account) {
        try{
            return accountDao.CreateAccount(account);
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean LinkAccount() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'LinkAccount'");
    }

}
