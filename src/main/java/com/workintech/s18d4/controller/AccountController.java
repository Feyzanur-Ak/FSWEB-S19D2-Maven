package com.workintech.s18d4.controller;

import com.workintech.s18d4.dto.AccountResponse;
import com.workintech.s18d4.dto.CustomerResponse;
import com.workintech.s18d4.entity.Account;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.AccountService;
import com.workintech.s18d4.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;
    private final CustomerService customerService;

    @Autowired
    public AccountController(AccountService accountService,CustomerService customerService) {
        this.accountService = accountService;
        this.customerService=customerService;
    }

    @GetMapping()
    List<Account> getAll(){
        return  accountService.findAll();
    }

    @GetMapping("/{id}")
    public  Account get(@PathVariable long id){
        return accountService.find(id);
    }

    @PostMapping("/{customerId}")//Acount bir costemıra bağlı bunu belirtmek lazım
    public AccountResponse save(@RequestBody Account account, @PathVariable long customerId){
        Customer customer=customerService.find(customerId);
        if(customer!=null){
            customer.getAccounts().add(account);//hem costımıra ekliyorum
            account.setCustomer(customer); //hem accounta
            accountService.save(account); //joinleri ayarladığım için karşılıklı yapar
        }
        else{
            throw new RuntimeException("no customer found");
        }
        return  new AccountResponse(account.getId(), account.getAccountName(),account.getMoneyAmount(),new CustomerResponse(customer.getId(),customer.getEmail(), customer.getSalary()));
    }

    @PutMapping("/{customerId}")
    public AccountResponse update(@PathVariable long customerId, @RequestBody Account account) {
        Customer customer = customerService.find(customerId);
        Account updatedAccount = null;

        for (Account account1 : customer.getAccounts()) {
            if (account.getId() == account1.getId()) {
                updatedAccount = account1;
                break;
            }
        }

        if (updatedAccount == null) {
            throw new RuntimeException("Böyle bir account yok");
        }

        int indexOfUpdatedAccount = customer.getAccounts().indexOf(updatedAccount);
        if (indexOfUpdatedAccount == -1) {
            throw new RuntimeException("Account not found in customer's account list");
        }

        customer.getAccounts().set(indexOfUpdatedAccount, account);
        account.setCustomer(customer);

        accountService.save(account);

        return new AccountResponse(account.getId(), account.getAccountName(), account.getMoneyAmount(),
                new CustomerResponse(customer.getId(), customer.getEmail(), customer.getSalary()));
    }
    @DeleteMapping("{id}")
    public AccountResponse remove(@PathVariable long id){
        Account account=accountService.find(id);
        if(account == null){
            throw new RuntimeException("no account find");
        }
        accountService.delete(id);

        return new AccountResponse(account.getId(), account.getAccountName(),account.getMoneyAmount(),
                new CustomerResponse(account.getCustomer().getId(),account.getCustomer().getEmail(),account.getCustomer().getSalary()));

        //Customera account üzerinden ulaştık
    }
}
