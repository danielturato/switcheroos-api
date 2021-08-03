package online.switcheroos.accounts.api.v1.controller;

import online.switcheroos.accounts.api.v1.dto.AccountDto;
import online.switcheroos.accounts.api.v1.dto.NewAccountDto;
import online.switcheroos.accounts.api.v1.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


public interface AccountController {

    @GetMapping("/{id}")
    AccountDto getAccountById(@PathVariable Long id);

    @GetMapping("/username/{username}")
    AccountDto getAccountByUsername(@PathVariable String username);

    @GetMapping("/email/{email}")
    AccountDto getAccountByEmail(@PathVariable String email);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AccountDto createAccount(@RequestBody NewAccountDto accountDto);
}
