package com.example.controller;

import com.example.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.request.CreateBankAccountRequest;
import com.example.dto.request.DepositRequest;
import com.example.dto.request.TransferRequest;
import com.example.dto.request.WithdrawRequest;
import com.example.dto.response.CreateBankAccountResponse;
import com.example.dto.response.GetAccountsOfCustomerResponse;
import com.example.dto.response.TransactionResponse;
import com.example.usecase.IBankAccountService;
import com.example.usecase.ITransactionService;
import com.example.usecase.TransactionResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = "http://localhost:3000")
public class AccountController {
    private final ITransactionService transactionService;
    private final IBankAccountService bankAccountService;

    @Autowired
    public AccountController(ITransactionService transactionService,
                            IBankAccountService bankAccountService){
            this.bankAccountService = bankAccountService;
            this.transactionService = transactionService;
    }
    @PostMapping
    public ResponseEntity<CreateBankAccountResponse> CreateBankAccount(@RequestBody CreateBankAccountRequest request){
        BankAccount bankAccount = BankAccount.builder()
                .id(request.getAccountId())
                .build();
        Customer customer = Customer.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .idOnTicsys(request.getIdOnTicsys())
                .password(request.getPassword())
                .build();
            
        String bankAccountId = bankAccountService.CreateAccount(customer,bankAccount);

       if (bankAccountId.equals("-1")) {
            return ResponseEntity.badRequest().body(CreateBankAccountResponse.builder()
                .message("Failed to create bank account")
                .build());
        }
       

        return ResponseEntity.ok(CreateBankAccountResponse.builder()
            .message("Create bank account successful")
            .accountId(bankAccountId)
            .build());
    }

    @GetMapping
    public ResponseEntity<GetAccountsOfCustomerResponse> GetAccountsOfCustomer(@RequestParam(name = "customerId") Integer customerId){
        var result = bankAccountService.GetAccountsOfCustomer(customerId);

        if(result == null){
            return ResponseEntity.badRequest().body(null);
        }
        if(result.isEmpty()){
            return ResponseEntity.badRequest().body(GetAccountsOfCustomerResponse.builder()
                .message("No accounts found for customer")
                .build());
        }

        return ResponseEntity.ok(GetAccountsOfCustomerResponse.builder()
            .accounts(result)
            .message("Get accounts of customer successful")
            .build());

    }
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> Deposit(@RequestBody DepositRequest depositRequest){

        var result = transactionService.Deposit(depositRequest.getAccountId(), depositRequest.getAmount());

        if(result == TransactionResult.UNKNOWN_ERROR){
            return ResponseEntity.badRequest().body(TransactionResponse.builder()
                .transactionResult(result)
                .message(result.name())
                .build());
        }
        else if (result != TransactionResult.SUCCESS) {
            return ResponseEntity.ok(TransactionResponse.builder()
                    .transactionResult(result)
                    .message(result.name())
                    .build());
        }
        return ResponseEntity.ok(TransactionResponse.builder()
            .transactionResult(result)
            .message("Deposit successful")
            .build());
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> Withdraw(@RequestBody WithdrawRequest withdrawDto){
        var result = transactionService.Withdraw(withdrawDto.getAccountId(), withdrawDto.getAmount());

        if(result == TransactionResult.UNKNOWN_ERROR){
            return ResponseEntity.badRequest().body(TransactionResponse.builder()
                .transactionResult(result)
                .message(result.name())
                .build());
        }
        else if (result != TransactionResult.SUCCESS) {
            return ResponseEntity.ok(TransactionResponse.builder()
                    .transactionResult(result)
                    .message(result.name())
                    .build());
        }
        return ResponseEntity.ok(TransactionResponse.builder()
            .transactionResult(result)
            .message("Withdraw successful")
            .build());
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> Transfer(@RequestBody TransferRequest transferDto){
        var result = transactionService.Transfer(transferDto.getFromAccountId(), 
                                                transferDto.getToAccountId(),
                                                transferDto.getAmount());

        if(result == TransactionResult.UNKNOWN_ERROR){
            return ResponseEntity.badRequest().body(TransactionResponse.builder()
                .transactionResult(result)
                .message(result.name())
                .build());
        }
        if(result != TransactionResult.SUCCESS){
            return ResponseEntity.ok(TransactionResponse.builder()
                    .transactionResult(result)
                    .message(result.name())
                    .build());
        }
        return ResponseEntity.ok(TransactionResponse.builder()
            .transactionResult(result)
            .message("Transfer successful")
            .build());
    }
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<Transaction>> GetTransactionsOfAccount(
        @PathVariable("accountId") String accountId,
        @RequestParam(required = false, name = "type") TransactionType type,
        @RequestParam(required = false, name = "fromAmount") Double fromAmount,
        @RequestParam(required = false, name = "toAmount") Double toAmount,
        @RequestParam(required = false, name = "fromCreatedDate") String fromCreatedDate,
        @RequestParam(required = false, name = "toCreatedDate") String toCreatedDate,
        @RequestParam(required = false, name = "counterPartyId") String counterPartyId,
        @RequestParam(required = false, name = "fromCreatedTime") String fromCreatedTime,
        @RequestParam(required = false, name = "toCreatedTime") String toCreatedTime
    ) {
        Map<TransactionFilterField, Object> filter = new HashMap<>();
        filter.put(TransactionFilterField.ACCOUNT_ID, accountId);
        if (type != null) {
            filter.put(TransactionFilterField.TYPE, type);
        }
        if (fromAmount != null) {
            filter.put(TransactionFilterField.FROM_AMOUNT, fromAmount);
        }
        if (toAmount != null) {
            filter.put(TransactionFilterField.TO_AMOUNT, toAmount);
        }
        if (fromCreatedDate != null) {
            filter.put(TransactionFilterField.FROM_CREATED_DATE, fromCreatedDate);
        }
        if (toCreatedDate != null) {
            filter.put(TransactionFilterField.TO_CREATED_DATE, toCreatedDate);
        }
        if (counterPartyId != null) {
            filter.put(TransactionFilterField.COUNTER_PARTY_ID, counterPartyId);
        }
        if (fromCreatedTime != null) {
            filter.put(TransactionFilterField.FROM_CREATED_TIME, fromCreatedTime);
        }
        if (toCreatedTime != null) {
            filter.put(TransactionFilterField.TO_CREATED_TIME, toCreatedTime);
        }
        var result = transactionService.GetTransactions(filter);

        if (result == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(result);
    }


    
}
