package online.switcheroos.api.v1.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import online.switcheroos.api.v1.dto.AccountDto;
import online.switcheroos.api.v1.dto.AuthAccountDto;
import online.switcheroos.api.v1.dto.MapStructMapper;
import online.switcheroos.api.v1.dto.NewAccountDto;
import online.switcheroos.api.v1.model.*;
import online.switcheroos.api.v1.repository.AccountRepository;
import online.switcheroos.dto.AuthAccountResponse;
import online.switcheroos.exception.AccountAlreadyExistsException;
import online.switcheroos.exception.AccountAuthException;
import online.switcheroos.exception.AccountNotFoundException;
import online.switcheroos.model.Role;
import online.switcheroos.model.Status;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    private final MapStructMapper mapper;

    //private final JobScheduler jobScheduler;

    @Override
    public AuthAccountResponse authenticateAccount(AuthAccountDto authAccountDto, HttpServletRequest request) {
        if (authAccountDto.getUsername() == null)
            throw new AccountAuthException("A username is required to authenticate an Account");

        Password password = new Password(authAccountDto.getPassword());
        // Password is expected to be hashed
        if (authAccountDto.getPassword().length() != Password.HASHED_PASSWORD_LENGTH)
            password.hash();

        Username username = new Username(authAccountDto.getUsername());
        AccountDto accountDto = findAccountByUsername(username.getValue());

        AuthAccountResponse authAccountResponse =
                accountDto.getPassword().equals(password.getValue()) ? successfulAuthResponse()  : failedAuthResponse();

        //jobScheduler.enqueue(() -> addLoginAttempt(accountDto.getId(), authAccountResponse, request));
        return authAccountResponse;
    }

    @Override
    public AccountDto findAccountById(UUID id) {
        Optional<Account> optAccount = repository.findById(id);
        Account account = unwrapAccount(optAccount, "The account with the ID: " + id + " does not exist");

        return mapper.accountToAccountDto(account);
    }

    @Override
    public AccountDto findAccountByUsername(String username) {
        Optional<Account> optAccount = repository.findByUsername(username);
        Account account = unwrapAccount(optAccount, "The account with the username: " + username + " does not exist");

        return mapper.accountToAccountDto(account);
    }

    @Override
    public AccountDto findAccountByEmail(String email) {
        Optional<Account> optAccount = repository.findByEmail(email);
        Account account = unwrapAccount(optAccount, "The account with the email: " + email + " does not exist");

        return mapper.accountToAccountDto(account);
    }

    @Override
    public AccountDto saveAccount(Account account) {
        Account savedAccount = repository.save(account);

        return mapper.accountToAccountDto(savedAccount);
    }

    @Override
    public AccountDto createAccount(NewAccountDto accountDto) {
        Account account = mapper.newAccountDtoAccount(accountDto);

        account.setRoles(Set.of(Role.USER));
        account.setStatus(Status.ACTIVE);
        account.getPassword().validate();
        account.getPassword().hash();

        try {
            return saveAccount(account);
        } catch (DataIntegrityViolationException ex) {
            throw new AccountAlreadyExistsException("The username or email provided is already in use on an existing Account");
            // TODO:drt - externalise exc messages
        }
    }

    @Override
    public void addLoginAttempt(UUID accountId, AuthAccountResponse authResponse, HttpServletRequest request) {
        Account account = unwrapAccount(repository.findById(accountId),
                "The account with the ID: " + accountId + " does not exist");

        LoginAttempt loginAttempt = LoginAttempt.builder()
                        .successful(authResponse.isAuthenticated())
                .ipAddress(new Inet("86.146.85.217"))
                .country("uk")
                .userAgent("Chrome")
                                .build();
        //account.addLoginAttempt(loginAttempt);
        repository.save(account);
    }

    private Account unwrapAccount(Optional<Account> account, String errMsg) {
        if (account.isPresent()) return account.get();

        throw new AccountNotFoundException(errMsg);
    }

    private AuthAccountResponse failedAuthResponse() {
        return new AuthAccountResponse(false, "Authentication failed. Invalid password for this account");
    }

    private AuthAccountResponse successfulAuthResponse() {
        return new AuthAccountResponse(true, "Authentication successful");
    }

}
