package com.example.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.example.entity.BankAccount;
import com.example.entity.Customer;
import com.example.usecase.dao.IBankAccountDao;
import com.example.usecase.dao.ICustomerDao;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BankAccountService implements IBankAccountService {
    private IBankAccountDao accountDao;
    private ICustomerDao customerDao;
    

    @Autowired
    public BankAccountService(IBankAccountDao accountDao, ICustomerDao customerDao) {
        this.customerDao = customerDao;
        this.accountDao = accountDao;
    }
    @Override
    @Transactional
    public String CreateAccount(Customer customer, BankAccount account) {
        try{
            Integer customerId = customerDao.CreateCustomer(customer);

            if (customerId < 0) {
                throw new Exception("Failed to create customer");
            }

            account.setCustomerId(customerId);
            account.setBalance(0.0);
            account.setCurrency("VND");
            account.setIsLocked(false);

            String bankAccountId =  accountDao.AddAccount(account);

            return bankAccountId;
        }catch(Exception e){
            log.error("Error creating account: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "-1";
        }
    }

    @Override
    public boolean LinkAccount() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'LinkAccount'");
    }
    @Override
    public List<BankAccount> GetAccountsOfCustomer(Integer customerId) {
        try{
            List<BankAccount> accounts = accountDao.GetAccountsOfCustomer(customerId);
            return accounts;
        }
        catch(Exception e){
            log.error("Error getting accounts of customer: {}", e.getMessage());
            return null;
        }
    }

}
