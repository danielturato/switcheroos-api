package online.switcheroos.accounts.api.v1.service;

import online.switcheroos.accounts.api.v1.dto.AccountDto;
import online.switcheroos.accounts.api.v1.dto.MapStructMapper;
import online.switcheroos.accounts.api.v1.dto.NewAccountDto;
import online.switcheroos.accounts.api.v1.model.Account;
import online.switcheroos.accounts.api.v1.repository.AccountRepository;
import online.switcheroos.accounts.exception.AccountAlreadyExistsException;
import online.switcheroos.accounts.exception.AccountNotFoundException;
import online.switcheroos.accounts.model.Role;
import online.switcheroos.accounts.model.Status;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    private final MapStructMapper mapper;

    public AccountServiceImpl(AccountRepository repository, MapStructMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public AccountDto findAccountById(Long id) {
        Optional<Account> optAccount = repository.findById(id);
        Account account = unwrapAccount(optAccount, "The account with the ID: " + id + " does not exist.");

        return mapper.accountToAccountDto(account);
    }

    @Override
    public AccountDto findAccountByUsername(String username) {
        Optional<Account> optAccount = repository.findByUsername(username);
        Account account = unwrapAccount(optAccount, "The account with the username: " + username + " does not exist.");

        return mapper.accountToAccountDto(account);
    }

    @Override
    public AccountDto findAccountByEmail(String email) {
        Optional<Account> optAccount = repository.findByEmail(email);
        Account account = unwrapAccount(optAccount, "The account with the email: " + email + " does not exist.");

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
            throw new AccountAlreadyExistsException("An account already exists with that email or username.");
        }
    }

    private Account unwrapAccount(Optional<Account> account, String errMsg) {
        if (account.isPresent()) return account.get();

        throw new AccountNotFoundException(errMsg);
    }
}
