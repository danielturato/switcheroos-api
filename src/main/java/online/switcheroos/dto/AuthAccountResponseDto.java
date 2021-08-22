package online.switcheroos.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthAccountResponseDto {

    @JsonIgnore
    private UUID accountId;

    private boolean authenticated;

    private String detail;
}
