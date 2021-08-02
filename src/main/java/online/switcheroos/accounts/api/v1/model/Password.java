package online.switcheroos.accounts.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.passay.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.Arrays;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Password {

    @Transient
    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Transient
    private static final PasswordValidator PASSWORD_VALIDATOR = passwordValidator();

    private static PasswordValidator passwordValidator() {
        return new PasswordValidator(
                Arrays.asList(
                        new LengthRule(8, 30),
                        new UppercaseCharacterRule(1),
                        new DigitCharacterRule(1),
                        new SpecialCharacterRule(1),
                        new WhitespaceRule()
                )
        );
    }

    @Column(name = "password")
    private String value;

    /**
     * Only has the password if it has been previously validated through validate()
     */
    public void hash() {
        this.value = PASSWORD_ENCODER.encode(this.value);
    }

    /**
     * Validate the password before hashing
     */
    public void validate() {
        RuleResult result = PASSWORD_VALIDATOR.validate(new PasswordData(this.value));
        if (!result.isValid()) {
            throw new IllegalArgumentException("The password is invalid: " + result.getDetails().get(0));
        }
    }
}
