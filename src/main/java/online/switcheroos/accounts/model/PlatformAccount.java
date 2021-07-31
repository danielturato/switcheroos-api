package online.switcheroos.accounts.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlatformAccount {

    @Enumerated(value = EnumType.STRING)
    @Type(type = "online.switcheroos.accounts.core.PostgreSQLEnumType")
    private Platform platform;

    private String platformUsername;
}
