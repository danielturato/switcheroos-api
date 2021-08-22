package online.switcheroos.api.v1.repository;

import online.switcheroos.api.v1.model.AuthenticationAttempt;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface AuthAttemptRepository extends PagingAndSortingRepository<AuthenticationAttempt, UUID> {
}
