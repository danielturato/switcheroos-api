package online.switcheroos.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.passay.*;
import org.springframework.lang.NonNull;
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
@ToString
public class Password implements Comparable<Password> {

    @Transient
    @JsonIgnore
    public static final int HASHED_PASSWORD_LENGTH = 60;

    @Transient
    @JsonIgnore
    public static final int MIN_PASSWORD_LENGTH = 8;

    @Transient
    @JsonIgnore
    public static final int MAX_PASSWORD_LENGTH = 30;

    @Transient
    @JsonIgnore
    private static final int PASSWORD_UPPERCASE_CHARS = 1;

    @Transient
    @JsonIgnore
    private static final int PASSWORD_DIGIT_CHARS = 1;

    @Transient
    @JsonIgnore
    private static final int PASSWORD_SPECIAL_CHARS = 1;


    @Transient
    @JsonIgnore
    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Transient
    @JsonIgnore
    private static final PasswordValidator PASSWORD_VALIDATOR = passwordValidator();

    private static PasswordValidator passwordValidator() {
        return new PasswordValidator(
                Arrays.asList(
                        new LengthRule(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH),
                        new UppercaseCharacterRule(PASSWORD_UPPERCASE_CHARS),
                        new DigitCharacterRule(PASSWORD_DIGIT_CHARS),
                        new SpecialCharacterRule(PASSWORD_SPECIAL_CHARS),
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
            throw new IllegalArgumentException("The password provided is invalid: " + result.getDetails().get(0));
        }
    }


    @Override
    public int compareTo(@NonNull Password that) {
        if (this.getValue() == null && that.getValue() == null) {
            return 0;
        } else if (this.getValue() == null) {
            return -1;
        } else if (that.getValue() == null) {
            return 1;
        } else {
            return this.getValue().compareTo(that.getValue());
        }
    }

}
