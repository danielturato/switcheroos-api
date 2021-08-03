package online.switcheroos.accounts.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import online.switcheroos.accounts.model.PlatformAccount;
import online.switcheroos.accounts.model.Role;
import online.switcheroos.accounts.model.Status;

import java.util.Set;

@Getter
@Setter
public class AccountDto {

    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    private String email;

    private String profilePicture;

    private boolean verified;

    private Status status;

    private Set<Role> roles;

    private Set<PlatformAccount> platformAccounts;

}
