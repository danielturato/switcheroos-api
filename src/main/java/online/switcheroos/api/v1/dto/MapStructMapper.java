package online.switcheroos.api.v1.dto;

import online.switcheroos.api.v1.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface MapStructMapper {

    @Mapping(source = "account.username.value", target = "username")
    @Mapping(source = "account.email.value", target = "email")
    @Mapping(source = "password.value", target = "password")
    AccountDto accountToAccountDto(Account account);

    @Mapping(source = "username", target = "username.value")
    @Mapping(source = "email", target = "email.value")
    @Mapping(source = "password", target = "password.value")
    Account accountDtoToAccount(AccountDto accountDto);

    @Mapping(source = "username", target = "username.value")
    @Mapping(source = "email", target = "email.value")
    @Mapping(source = "password", target = "password.value")
    Account newAccountDtoAccount(NewAccountDto newAccountDto);

    PatchAccountDto accountToPatchAccountDto(Account account);
}
