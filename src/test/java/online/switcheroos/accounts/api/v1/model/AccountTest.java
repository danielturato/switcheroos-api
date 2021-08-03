package online.switcheroos.accounts.api.v1.model;

import online.switcheroos.accounts.api.v1.repository.AccountRepository;
import online.switcheroos.accounts.model.Platform;
import online.switcheroos.accounts.model.PlatformAccount;
import online.switcheroos.accounts.model.Role;
import online.switcheroos.accounts.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class AccountTest {

    private Account account;

    private final TestEntityManager testEntityManager;

    private final AccountRepository accountRepository;

    @Autowired
    public AccountTest(TestEntityManager testEntityManager, AccountRepository accountRepository) {
        this.testEntityManager = testEntityManager;
        this.accountRepository = accountRepository;
    }

    @BeforeEach
    void setUp() {
        this.account = Account.builder()
                .username(new Username("testing"))
                .email(new Email("testing@switcheroos.online"))
                .profilePicture("https://google.com")
                .status(Status.ACTIVE)
                .verified(false)
                .roles(Set.of(Role.USER))
                .platformAccounts(Set.of(new PlatformAccount(Platform.PSN, "switcheroos"))).build();
    }

    @Test
    @DisplayName("An account can be persisted successfully")
    void accountCanBePersisted() {
        assertThatNoException().isThrownBy(() -> this.testEntityManager.persist(this.account));
    }

    @Test
    @DisplayName("An account can be found by it's username")
    void accountCanBeFoundByUsername() {
        this.accountRepository.save(this.account);
        assertThatNoException().isThrownBy(() -> this.accountRepository.findByUsername("testing").get());
        assertThat(this.accountRepository.findByUsername("testing").get().getUsername().getValue())
                .isEqualTo("testing");
    }

    @Test
    @DisplayName("An account can be found by it's email")
    void accountCanBeFoundByEmail() {
        this.accountRepository.save(this.account);
        assertThatNoException().isThrownBy(() -> this.accountRepository.findByEmail("testing@switcheroos.online").get());
        assertThat(this.accountRepository.findByEmail("testing@switcheroos.online").get().getEmail().getValue())
                .isEqualTo("testing@switcheroos.online");
    }

    @Test
    @DisplayName("An account can be found by it's id")
    void accountCanBeFoundById() {
       Account savedAccount = this.accountRepository.save(this.account);
        assertThatNoException().isThrownBy(() -> this.accountRepository.findById(savedAccount.getId()).get());
        assertThat(this.accountRepository.findById(savedAccount.getId()).get().getUsername().getValue())
                .isEqualTo("testing");
    }
}