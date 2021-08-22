package online.switcheroos.accounts.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import lombok.ToString;
import org.apache.commons.validator.routines.EmailValidator;

import static org.apache.commons.lang3.Validate.*;


@Embeddable
@Getter
@NoArgsConstructor
@ToString
public class Email {

    @Transient
    @JsonIgnore
    private final EmailValidator emailValidator = EmailValidator.getInstance();

    @Column(name = "email", unique = true)
    private String value;

    public Email(final String value) {
        setValue(value);
    }

    public void setValue(String value) {
        this.value = validateAndTrim(value);
    }

    public String validateAndTrim(String value) {
        notBlank(value, "The email provided was either null or blank");

        final String trimmed = value.trim();
        if (!emailValidator.isValid(trimmed)) {
            throw new IllegalArgumentException("The email provided is not valid");
        }

        return trimmed;
    }
}
