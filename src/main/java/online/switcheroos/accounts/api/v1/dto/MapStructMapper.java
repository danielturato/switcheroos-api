package online.switcheroos.accounts.api.v1.dto;

import online.switcheroos.accounts.api.v1.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface MapStructMapper {

    @Mapping(source = "account.username.value", target = "username")
    @Mapping(source = "account.email.value", target = "email")
    @Mapping(source = "account.password.value", target = "password")
    AccountDto accountToAccountDto(Account account);

    @Mapping(source = "username", target = "username.value")
    @Mapping(source = "email", target = "email.value")
    @Mapping(source = "password", target = "password.value")
    Account accountDtoToAccount(AccountDto accountDto);
}
