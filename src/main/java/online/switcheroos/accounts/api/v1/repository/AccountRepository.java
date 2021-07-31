package online.switcheroos.accounts.api.v1.repository;

import online.switcheroos.accounts.api.v1.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
