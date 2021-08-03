package online.switcheroos.accounts.api.v1.service;

import online.switcheroos.accounts.api.v1.dto.AccountDto;

public interface AccountService {

    AccountDto findAccountById(Long id);
}
