package online.switcheroos.accounts.api.v1.service;

import online.switcheroos.accounts.api.v1.dto.AccountDto;
import online.switcheroos.accounts.api.v1.dto.MapStructMapper;
import online.switcheroos.accounts.api.v1.model.Account;
import online.switcheroos.accounts.api.v1.repository.AccountRepository;
import online.switcheroos.accounts.exception.AccountNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    private final AccountRepository repository;

    private final MapStructMapper mapper;

    public AccountServiceImpl(AccountRepository repository, MapStructMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public AccountDto findAccountById(Long id) {
        Optional<Account> account = repository.findById(id);

        if (account.isPresent()) {
            return mapper.accountToAccountDto(account.get());
        }

        throw new AccountNotFoundException("The account with the ID: " + id + " does not exist.");
    }
}
