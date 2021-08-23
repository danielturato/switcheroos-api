package online.switcheroos.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.switcheroos.api.v1.model.Email;
import online.switcheroos.api.v1.model.Username;
import online.switcheroos.model.PlatformAccount;
import online.switcheroos.model.SocialAccount;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchAccountDto {

    private Username username;

    private Email email;

    @JsonProperty("platform_accounts")
    private Set<PlatformAccount> platformAccounts;

    @JsonProperty("social_accounts")
    private Set<SocialAccount> socialAccounts;
}
