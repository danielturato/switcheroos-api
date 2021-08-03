package online.switcheroos.accounts.api.v1.controller;

import online.switcheroos.accounts.api.v1.dto.AccountDto;
import online.switcheroos.accounts.api.v1.dto.NewAccountDto;
import online.switcheroos.accounts.api.v1.model.Account;
import online.switcheroos.accounts.api.v1.service.AccountService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/accounts")
public class AccountControllerImpl implements AccountController{

    private final AccountService accountService;

    public AccountControllerImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public AccountDto getAccountById(Long id) {
        return accountService.findAccountById(id);
    }

    @Override
    public AccountDto getAccountByUsername(String username) {
        return accountService.findAccountByUsername(username);
    }

    @Override
    public AccountDto getAccountByEmail(String email) {
        return accountService.findAccountByEmail(email);
    }

    @Override
    public AccountDto createAccount(@RequestBody NewAccountDto accountDto) {
        return accountService.createAccount(accountDto);
    }
}
