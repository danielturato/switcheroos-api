package online.switcheroos.api.v1.service;

import com.github.fge.jsonpatch.JsonPatch;
import online.switcheroos.api.v1.dto.AccountDto;
import online.switcheroos.api.v1.dto.AuthAccountDto;
import online.switcheroos.api.v1.dto.NewAccountDto;
import online.switcheroos.api.v1.model.Account;
import online.switcheroos.api.v1.model.Inet;
import online.switcheroos.dto.AuthAccountResponseDto;
import org.jobrunr.jobs.annotations.Job;

import java.util.UUID;

public interface AccountService {

    AuthAccountResponseDto authenticateAccount(AuthAccountDto authAccountDto);

    AccountDto findAccountDtoById(UUID id);

    Account findAccountById(UUID id);

    AccountDto findAccountDtoByUsername(String username);

    Account findAccountByUsername(String username);

    AccountDto findAccountDtoByEmail(String email);

    Account findAccountByEmail(String email);

    AccountDto saveAccount(Account account);

    AccountDto createAccount(NewAccountDto accountDto);

    Account patchAccount(JsonPatch patch, Account account);

    @Job(name = "Log authentication attempt for account ID - %0", retries = 2)
    void logAuthAttempt(UUID accountId, boolean authenticated, Inet requestIp, String userAgent);

}
