package online.switcheroos.accounts;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.DatabasePopulator;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@SpringBootApplication
public class AccountsApplication {

	@Value("${scripts.locations.db-schema}")
	private String schemaScript;

	@Value("${scripts.locations.db-data}")
	private String dataScript;

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

	@Bean
	@Profile("dev")
	ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		initializer.setDatabasePopulator(databasePopulator());

		return initializer;
	}

	private DatabasePopulator databasePopulator() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource(schemaScript));
		populator.addScript(new ClassPathResource(dataScript));

		return populator;
	}

}

