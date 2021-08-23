package online.switcheroos.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import online.switcheroos.model.PlatformAccount;
import online.switcheroos.model.Role;
import online.switcheroos.model.SocialAccount;
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

    @JsonProperty("profile_picture")
    private String profilePicture;

    private boolean verified;

    private Status status;

    private Set<Role> roles;

    @JsonProperty("platform_accounts")
    private Set<PlatformAccount> platformAccounts;

    @JsonProperty("social_accounts")
    private Set<SocialAccount> socialAccounts;

    public AccountDto() {}
}
