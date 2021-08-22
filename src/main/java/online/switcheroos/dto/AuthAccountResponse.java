package online.switcheroos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthAccountResponse {

    private final boolean authenticated;

    private final String detail;
}
