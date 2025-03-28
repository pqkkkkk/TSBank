package com.example.usecase;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
public class TransactionServiceV2 implements ITransactionService {

    @Override
    public TransactionResult Transfer(String fromAccountId, String toAccountId, Integer amount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Transfer'");
    }

    @Override
    public TransactionResult Deposit(String accountId, Integer amount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Deposit'");
    }

    @Override
    public TransactionResult Withdraw(String accountId, Integer amount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Withdraw'");
    }

}
