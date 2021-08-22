package online.switcheroos.api.v1.service;

import online.switcheroos.api.v1.dto.AccountDto;
import online.switcheroos.api.v1.dto.MapStructMapper;
import online.switcheroos.api.v1.dto.NewAccountDto;
import online.switcheroos.api.v1.model.Account;
import online.switcheroos.api.v1.model.Email;
import online.switcheroos.api.v1.model.Password;
import online.switcheroos.api.v1.model.Username;
import online.switcheroos.api.v1.repository.AccountRepository;
import online.switcheroos.model.Platform;
import online.switcheroos.model.PlatformAccount;
import online.switcheroos.model.Role;
import online.switcheroos.model.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;


import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountServiceImplTest {

    private Account account;

    private AccountDto accountDto;

    private Password passwordSpy;

    private AccountService accountService;

    @Mock
    private AccountRepository repository;

    @Mock
    private MapStructMapper mapper;

    @BeforeAll
    void setup() {
        MockitoAnnotations.openMocks(this);
        accountService = new AccountServiceImpl(repository, mapper);
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(repository, mapper);

        Password password = new Password("PassW0rd!)");
        this.passwordSpy = Mockito.spy(password);

        UUID id = UUID.randomUUID();

        this.account = Account.builder()
                .id(id)
                .password(passwordSpy)
                .username(new Username("testing"))
                .email(new Email("testing@switcheroos.online"))
                .profilePicture("https://switcheroos.online")
                .status(Status.ACTIVE)
                .verified(false)
                .roles(Set.of(Role.USER))
                .platformAccounts(Set.of(new PlatformAccount(Platform.PSN, "switcheroos"))).build();

        this.accountDto = AccountDto.builder()
                .id(id)
                .username("testing")
                .email("testing@switcheroos.online")
                .profilePicture("https://switcheroos.online")
                .roles(Set.of(Role.USER))
                .platformAccounts(Set.of(new PlatformAccount(Platform.PSN, "switcheroos")))
                .status(Status.ACTIVE)
                .verified(false)
                .build();
    }

    @Test
    void getAccountByIdReturnsAccountDto() {
        given(repository.findById(any(UUID.class))).willReturn(Optional.of(this.account));
        given(mapper.accountToAccountDto(any(Account.class))).willReturn(this.accountDto);

        AccountDto accountDto = accountService.findAccountById(account.getId());

        verify(repository).findById(account.getId());
        verify(mapper).accountToAccountDto(any(Account.class));

        assertThat(accountDto.getId()).isEqualTo(this.account.getId());
    }

    @Test
    void getAccountByUsernameReturnsAccountDto() {
        given(repository.findByUsername(any(String.class))).willReturn(Optional.of(this.account));
        given(mapper.accountToAccountDto(any(Account.class))).willReturn(this.accountDto);

        AccountDto accountDto = accountService.findAccountByUsername("testing");

        verify(repository).findByUsername(any(String.class));
        verify(mapper).accountToAccountDto(any(Account.class));

        assertThat(accountDto.getUsername()).isEqualTo(this.account.getUsername().getValue());
    }

    @Test
    void getAccountByEmailReturnsAccountDto() {
        given(repository.findByEmail(any(String.class))).willReturn(Optional.of(this.account));
        given(mapper.accountToAccountDto(any(Account.class))).willReturn(this.accountDto);

        AccountDto accountDto = accountService.findAccountByEmail("testing@switcheroos.online");

        verify(repository).findByEmail(any(String.class));
        verify(mapper).accountToAccountDto(any(Account.class));

        assertThat(accountDto.getEmail()).isEqualTo(this.account.getEmail().getValue());
    }

    @Test
    void createAccountCreatesAccount() {
        NewAccountDto newAccountDto = new NewAccountDto("testing", "PassW0rd!", "testing@switcheroos.online");

        given(mapper.newAccountDtoAccount(any(NewAccountDto.class))).willReturn(this.account);
        given(repository.save(any(Account.class))).willReturn(this.account);
        given(mapper.accountToAccountDto(any(Account.class))).willReturn(this.accountDto);

        doAnswer(invoc -> {
            return null;
        }).when(passwordSpy).validate();
        doAnswer(invoc -> {
            return null;
        }).when(passwordSpy).hash();

        AccountDto accountDto = accountService.createAccount(newAccountDto);

        verify(mapper).accountToAccountDto(any(Account.class));
        verify(mapper).newAccountDtoAccount(any(NewAccountDto.class));
        verify(repository).save(any(Account.class));
        verify(passwordSpy).validate();
        verify(passwordSpy).hash();

        assertThat(accountDto.getEmail()).isEqualTo(newAccountDto.getEmail());
        assertThat(accountDto.getUsername()).isEqualTo(newAccountDto.getUsername());
    }

//    @Test
//    void updateAccountUpdatesAccount() {
//        this.accountDto.setUsername("testing-new-user");
//
//        given(mapper.accountDtoToAccount(any(AccountDto.class))).willReturn(this.account);
//        given(repository.save(any(Account.class))).willReturn(this.account);
//        given(mapper.accountToAccountDto(any(Account.class))).willReturn(this.accountDto);
//
//        AccountDto accountDto = accountService.updateAccount(this.accountDto.getId(), this.accountDto);
//
//        verify(mapper).accountDtoToAccount(any(AccountDto.class));
//        verify(mapper).accountToAccountDto(any(Account.class));
//        verify(repository).save(any(Account.class));
//
//        assertThat(accountDto.getUsername()).isEqualTo("testing-new-user");
//    }
}