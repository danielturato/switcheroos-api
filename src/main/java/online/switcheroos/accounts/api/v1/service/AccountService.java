package online.switcheroos.accounts.api.v1.service;

import online.switcheroos.accounts.api.v1.dto.AccountDto;
import online.switcheroos.accounts.api.v1.dto.NewAccountDto;
import online.switcheroos.accounts.api.v1.model.Account;

import java.util.Map;
import java.util.UUID;

public interface AccountService {

    AccountDto findAccountById(UUID id);

    AccountDto findAccountByUsername(String username);

    AccountDto findAccountByEmail(String email);

    AccountDto saveAccount(Account account);

    AccountDto createAccount(NewAccountDto accountDto);

}
