package online.switcheroos.accounts.api.v1.controller;

import lombok.RequiredArgsConstructor;
import online.switcheroos.accounts.api.v1.dto.AccountDto;
import online.switcheroos.accounts.api.v1.dto.AuthAccountDto;
import online.switcheroos.accounts.api.v1.dto.NewAccountDto;
import online.switcheroos.accounts.api.v1.dto.ResourceResponseDto;
import online.switcheroos.accounts.api.v1.service.AccountService;
import online.switcheroos.accounts.dto.AuthAccountResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/v1/accounts")
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController{

    private final AccountService accountService;

    @Override
    public AuthAccountResponse authenticateAccount(AuthAccountDto authAccountDto, HttpServletRequest request) {
        return accountService.authenticateAccount(authAccountDto, request);
    }

    @Override
    public AccountDto getAccountById(UUID id) {
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
    public ResourceResponseDto createAccount(@RequestBody NewAccountDto accountDto) {
        AccountDto account = accountService.createAccount(accountDto);
        return new ResourceResponseDto("/api/v1/accounts/" + account.getId());
    }

}
