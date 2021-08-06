package online.switcheroos.accounts.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import online.switcheroos.accounts.core.PostgreSQLEnumType;
import online.switcheroos.accounts.model.PlatformAccount;
import online.switcheroos.accounts.model.Role;
import online.switcheroos.accounts.model.Status;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TypeDef(
        name = "psql_enum",
        typeClass = PostgreSQLEnumType.class
)
public class Account implements Persistable<UUID> {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private Username username;

    private Password password;

    private Email email;

    private String profilePicture;

    private boolean verified;

    @Enumerated(EnumType.STRING)
    @Type(type = "psql_enum")
    private Status status;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    @Type(type = "psql_enum")
    @CollectionTable(name = "accounts_roles", joinColumns = @JoinColumn(name = "account_id"))
    @Column(name = "role")
    private Set<Role> roles;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "platform_accounts", joinColumns = @JoinColumn(name = "account_id"))
    private Set<PlatformAccount> platformAccounts;

    public Account(Username username, Password password, Email email, String profilePicture, boolean verified,
                   Status status, Set<Role> roles, Set<PlatformAccount> platformAccounts) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.profilePicture = profilePicture;
        this.verified = verified;
        this.status = status;
        this.roles = roles;
        this.platformAccounts = platformAccounts;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
