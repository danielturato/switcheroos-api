package online.switcheroos.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import online.switcheroos.model.PlatformAccount;
import online.switcheroos.model.Role;
import online.switcheroos.model.Status;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AccountDto {

    private UUID id;

    private String username;

    @JsonIgnore
    private String password;

    private String email;

    private String profilePicture;

    private boolean verified;

    private Status status;

    private Set<Role> roles;

    private Set<PlatformAccount> platformAccounts;

    public AccountDto() {}
}
