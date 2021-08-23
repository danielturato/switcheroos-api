package online.switcheroos.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class SocialAccount {

    @Enumerated(value = EnumType.STRING)
    @Type(type = "online.switcheroos.core.PostgreSQLEnumType")
    private Social social;

    @JsonProperty("social_username")
    private String socialUsername;
}
