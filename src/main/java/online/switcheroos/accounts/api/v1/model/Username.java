package online.switcheroos.accounts.api.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static org.apache.commons.lang3.Validate.*;

@Embeddable
@Getter
@NoArgsConstructor
public class Username {

    protected static final int MINIMUM_LENGTH = 3;
    protected static final int MAXIMUM_LENGTH = 15;
    protected static final String VALID_CHARACTERS = "[A-Za-z0-9_-]+";

    @Column(name = "username", unique = true)
    private String value;

    public Username(final String value) {
        setValue(value);
    }

    public void setValue(String value) {
        this.value = validateAndTrim(value);
    }

    public String validateAndTrim(String value) {
        notBlank(value);

        final String trimmed = value.trim();
        inclusiveBetween(MINIMUM_LENGTH, MAXIMUM_LENGTH, trimmed.length());
        matchesPattern(trimmed, VALID_CHARACTERS, "Allowed characters are %s", VALID_CHARACTERS);

        return trimmed;
    }
}
