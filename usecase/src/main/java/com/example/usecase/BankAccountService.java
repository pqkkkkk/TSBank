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
    @Transactional
    public LinkBankAccountResult LinkAccount(String idOnTicsys, String accountId,
                               String accountOwnerName) {
        try{
            BankAccount account =  accountDao.GetAccountById(accountId);
            if (account == null) {
                return LinkBankAccountResult.BANK_ACCOUNT_NOT_FOUND;
            }

            Customer customer = customerDao.GetCustomerById(account.getCustomerId());
            customer.setFullName(customer.getFullName().replace(" ", ""));
            if(!customer.getFullName().equals(accountOwnerName)){
                return LinkBankAccountResult.WRONG_BANK_ACCOUNT_OWNER_NAME;
            }

            if(customer.getIdOnTicsys() != null){
                return LinkBankAccountResult.BANK_ACCOUNT_IS_ALREADY_LINKED;
            }

            customer.setIdOnTicsys(idOnTicsys);
            customerDao.UpdateCustomer(customer);

            return LinkBankAccountResult.SUCCESS;
        }
        catch (Exception e){
            log.error("Error linking account: {}", e.getMessage());
            return LinkBankAccountResult.UNKNOWN_ERROR;
        }
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
