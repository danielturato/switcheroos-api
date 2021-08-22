package online.switcheroos.api.v1.service;

import online.switcheroos.api.v1.dto.AccountDto;
import online.switcheroos.api.v1.dto.AuthAccountDto;
import online.switcheroos.api.v1.dto.NewAccountDto;
import online.switcheroos.api.v1.model.Account;
import online.switcheroos.dto.AuthAccountResponse;
import org.jobrunr.jobs.annotations.Job;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public interface AccountService {

    AuthAccountResponse authenticateAccount(AuthAccountDto authAccountDto, HttpServletRequest request);

    AccountDto findAccountById(UUID id);

    AccountDto findAccountByUsername(String username);

    AccountDto findAccountByEmail(String email);

    AccountDto saveAccount(Account account);

    AccountDto createAccount(NewAccountDto accountDto);

    @Job(name = "Add login attempt for account ID: %0", retries = 2)
    void addLoginAttempt(UUID accountId, AuthAccountResponse authResponse, HttpServletRequest request);

}
