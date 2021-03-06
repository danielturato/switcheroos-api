package online.switcheroos.api.v1.repository;

import online.switcheroos.api.v1.model.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends PagingAndSortingRepository<Account, UUID> {

    @Query("SELECT a from Account a WHERE a.username.value = ?1")
    Optional<Account> findByUsername(String username);

    @Query("SELECT a from Account a WHERE a.email.value = ?1")
    Optional<Account> findByEmail(String email);
}
