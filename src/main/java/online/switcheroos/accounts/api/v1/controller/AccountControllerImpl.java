package online.switcheroos.accounts.api.v1.controller;

import online.switcheroos.accounts.api.v1.dto.AccountDto;
import online.switcheroos.accounts.api.v1.model.Account;
import online.switcheroos.accounts.api.v1.service.AccountService;
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
    public Account getAccountByUsername(String username) {
        return null;
    }
}
