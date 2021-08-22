package online.switcheroos.accounts.api.v1.controller;

import online.switcheroos.accounts.api.v1.dto.AccountDto;
import online.switcheroos.accounts.api.v1.dto.AuthAccountDto;
import online.switcheroos.accounts.api.v1.dto.NewAccountDto;
import online.switcheroos.accounts.api.v1.dto.ResourceResponseDto;
import online.switcheroos.accounts.api.v1.model.Account;
import online.switcheroos.accounts.dto.AuthAccountResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;


public interface AccountController {

    @PostMapping("/authenticate")
    AuthAccountResponse authenticateAccount(@RequestBody AuthAccountDto authAccountDto, HttpServletRequest request);

    @GetMapping("/{id}")
    AccountDto getAccountById(@PathVariable UUID id);

    @GetMapping("/username/{username}")
    AccountDto getAccountByUsername(@PathVariable String username);

    @GetMapping("/email/{email}")
    AccountDto getAccountByEmail(@PathVariable String email);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResourceResponseDto createAccount(@RequestBody NewAccountDto accountDto);

}
