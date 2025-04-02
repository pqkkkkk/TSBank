package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.request.CreateBankAccountRequest;
import com.example.dto.request.DepositRequest;
import com.example.dto.request.TransferRequest;
import com.example.dto.request.WithdrawRequest;
import com.example.dto.response.CreateBankAccountResponse;
import com.example.dto.response.GetAccountsOfCustomerResponse;
import com.example.dto.response.TransactionResponse;
import com.example.entity.BankAccount;
import com.example.entity.Customer;
import com.example.usecase.IBankAccountService;
import com.example.usecase.ITransactionService;
import com.example.usecase.TransactionResult;

@RestController
@RequestMapping("/api/account")
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
    public ResponseEntity<TransactionResponse> Deposit(@RequestBody DepositRequest depositDto){

        var result = transactionService.Deposit(depositDto.getAccountId(), depositDto.getAmount());

        if(result != TransactionResult.SUCCESS){
            return ResponseEntity.badRequest().body(TransactionResponse.builder()
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

        if(result != TransactionResult.SUCCESS){
            return ResponseEntity.badRequest().body(TransactionResponse.builder()
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

        if(result != TransactionResult.SUCCESS){
            return ResponseEntity.badRequest().body(TransactionResponse.builder()
                .transactionResult(result)
                .message(result.name())
                .build());
        }
        return ResponseEntity.ok(TransactionResponse.builder()
            .transactionResult(result)
            .message("Transfer successful")
            .build());
    }


    
}
