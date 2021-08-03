package online.switcheroos.accounts.api.v1.service;

import online.switcheroos.accounts.api.v1.dto.AccountDto;
import online.switcheroos.accounts.api.v1.dto.NewAccountDto;
import online.switcheroos.accounts.api.v1.model.Account;

public interface AccountService {

    AccountDto findAccountById(Long id);

    AccountDto findAccountByUsername(String username);

    AccountDto findAccountByEmail(String email);

    AccountDto saveAccount(Account account);

    AccountDto createAccount(NewAccountDto accountDto);
}
