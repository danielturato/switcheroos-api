package online.switcheroos.accounts;

import online.switcheroos.accounts.api.v1.model.Account;
import online.switcheroos.accounts.api.v1.model.Email;
import online.switcheroos.accounts.api.v1.model.Password;
import online.switcheroos.accounts.api.v1.model.Username;
import online.switcheroos.accounts.api.v1.repository.AccountRepository;
import online.switcheroos.accounts.model.Platform;
import online.switcheroos.accounts.model.PlatformAccount;
import online.switcheroos.accounts.model.Role;
import online.switcheroos.accounts.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;


@SpringBootApplication
public class AccountsApplication implements CommandLineRunner {

	@Autowired
	AccountRepository accountRepository;

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Password password = new Password("Happ1y!");
		password.hash();
		Account account = Account.builder()
				.username(new Username("testing"))
				.password(password)
				.email(new Email("danielturato@hotmail.co.uk"))
				.profilePicture("asdassad")
				.status(Status.ACTIVE)
				.verified(false)
				.roles(Set.of(Role.USER))
				.platformAccounts(Set.of(new PlatformAccount(Platform.PSN, "asas"))).build();
		accountRepository.save(account);

		System.out.println(accountRepository.findByUsername("testing"));
	}
}

