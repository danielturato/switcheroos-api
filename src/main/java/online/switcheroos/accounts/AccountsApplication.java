package online.switcheroos.accounts;

import online.switcheroos.accounts.api.v1.model.Account;
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
		accountRepository.save(new Account("biasad", "asdad@gmail.com",
				"https://facebook.com", false, Status.ACTIVE,
				Set.of(Role.ADMIN), Set.of(new PlatformAccount(Platform.PSN, "Remedy0_"))));

		System.out.println(accountRepository.findById(2L));
	}
}

