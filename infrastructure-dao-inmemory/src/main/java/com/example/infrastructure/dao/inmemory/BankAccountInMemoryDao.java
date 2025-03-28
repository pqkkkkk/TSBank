package com.example.infrastructure.dao.inmemory;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.example.entity.BankAccount;
import com.example.usecase.dao.IBankAccountDao;

@Repository
@Profile("inmemory")
public class BankAccountInMemoryDao implements IBankAccountDao {

    @Override
    public boolean AddAccount(BankAccount account) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'AddAccount'");
    }

    @Override
    public boolean UpdateAccount(BankAccount account) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'UpdateAccount'");
    }

    @Override
    public BankAccount GetAccountById(String accountId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GetAccountById'");
    }

}
