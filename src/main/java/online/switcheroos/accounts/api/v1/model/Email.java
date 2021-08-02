package online.switcheroos.accounts.api.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.apache.commons.validator.routines.EmailValidator;

import static org.apache.commons.lang3.Validate.*;


@Embeddable
@Getter
@NoArgsConstructor
public class Email {

    @Transient
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
        notBlank(value);

        final String trimmed = value.trim();
        if (!emailValidator.isValid(trimmed)) {
            throw new IllegalArgumentException("The value is not a valid email");
        }

        return trimmed;
    }
}
