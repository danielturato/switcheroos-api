package online.switcheroos.api.v1.service;

import lombok.AllArgsConstructor;
import online.switcheroos.api.v1.dto.AccountDto;
import online.switcheroos.api.v1.dto.AuthAccountDto;
import online.switcheroos.api.v1.dto.MapStructMapper;
import online.switcheroos.api.v1.dto.NewAccountDto;
import online.switcheroos.api.v1.model.*;
import online.switcheroos.api.v1.repository.AccountRepository;
import online.switcheroos.dto.AuthAccountResponseDto;
import online.switcheroos.exception.AccountAlreadyExistsException;
import online.switcheroos.exception.AccountAuthException;
import online.switcheroos.exception.AccountNotFoundException;
import online.switcheroos.model.Role;
import online.switcheroos.model.Status;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    private final MapStructMapper mapper;

    @Override
    public AuthAccountResponseDto authenticateAccount(AuthAccountDto authAccountDto) {
        if (authAccountDto.getUsername() == null)
            throw new AccountAuthException("A username is required to authenticate an Account");

        Account account = findAccountByUsername(authAccountDto.getUsername());
        Password accountPassword = account.getPassword();

        return accountPassword.matches(authAccountDto.getPassword())
                ? successfulAuthResponse(account.getId())  : failedAuthResponse(account.getId());
    }

    @Override
    public AccountDto findAccountDtoById(UUID id) {
        Account account = findAccountById(id);

        return mapper.accountToAccountDto(account);
    }

    @Override
    public Account findAccountById(UUID id) {
        Optional<Account> optAccount = repository.findById(id);
        return unwrapAccount(optAccount, "The account with the ID: " + id + " does not exist");
    }

    @Override
    public AccountDto findAccountDtoByUsername(String username) {
        Account account = findAccountByUsername(username);

        return mapper.accountToAccountDto(account);
    }

    @Override
    public Account findAccountByUsername(String username) {
        Optional<Account> optAccount = repository.findByUsername(username);
        return unwrapAccount(optAccount, "The account with the username: " + username + " does not exist");
    }

    @Override
    public AccountDto findAccountDtoByEmail(String email) {
        Account account = findAccountByEmail(email);

        return mapper.accountToAccountDto(account);
    }

    @Override
    public Account findAccountByEmail(String email) {
        Optional<Account> optAccount = repository.findByEmail(email);
        return unwrapAccount(optAccount, "The account with the email: " + email + " does not exist");
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
    public void logAuthAttempt(UUID accountId, boolean authenticated, Inet requestIp, String userAgent) {
        Account account = findAccountById(accountId);

        AuthenticationAttempt attempt = AuthenticationAttempt.builder()
                .account(account)
                .successful(authenticated)
                .timestamp(new Date())
                .userAgent(userAgent)
                .ipAddress(requestIp)
                .build();
        account.addLoginAttempt(attempt);
        //TODO:drt - add country support
        saveAccount(account);
    }

    private Account unwrapAccount(Optional<Account> account, String errMsg) {
        if (account.isPresent()) return account.get();

        throw new AccountNotFoundException(errMsg);
    }

    private AuthAccountResponseDto failedAuthResponse(UUID accountId) {
        return new AuthAccountResponseDto(accountId, false, "Authentication failed. Invalid password for this account");
    }

    private AuthAccountResponseDto successfulAuthResponse(UUID accountId) {
        return new AuthAccountResponseDto(accountId,true, "Authentication successful");
    }

}
