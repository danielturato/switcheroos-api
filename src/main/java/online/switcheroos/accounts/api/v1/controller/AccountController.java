package online.switcheroos.accounts.api.v1.controller;

import online.switcheroos.accounts.api.v1.dto.AccountDto;
import online.switcheroos.accounts.api.v1.model.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


public interface AccountController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    AccountDto getAccountById(@PathVariable Long id);

    @GetMapping("/username/{username}")
    Account getAccountByUsername(@PathVariable String username);
}
