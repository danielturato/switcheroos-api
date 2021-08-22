package online.switcheroos.accounts.api.v1.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class EmailTest {

    @Test
    @DisplayName("Email value is able to bet set/changed")
    void emailValueCanBeSet() {
        Email email = new Email("test@switcheass.com");
        email.setValue("test@switcheroos.com");

        assertThat(email.getValue()).isEqualTo("test@switcheroos.com");
    }

    @Test
    @DisplayName("Email throws an exception if the value is not an email")
    void emailIsNotValidAndThrowsException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Email("daniel.com"));
    }

    @Test
    @DisplayName("Email throws an exception if the value is blank (length 0)")
    void emailThrowsExceptionIfValueIsBlank() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Email(""));
    }

    @Test
    @DisplayName("Email throws an exception if the value is null")
    void emailThrowsExceptionIfValueIsNull() {
        assertThatNullPointerException().isThrownBy(() -> new Email(null));
    }

    @Test
    @DisplayName("Email throws an exception if the value blank (length > 0")
    void emailThrowsExceptionIfValueIsBlankWithLengthGreaterThanZero() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Email("         "));
    }

    @Test
    @DisplayName("Email will trim the whitespace of a parsed email")
    void emailTrimsBlankWhiteSpace() {
        Email email = new Email("                     test@switcheroos.online                    ");
        assertThat(email.getValue()).isEqualTo("test@switcheroos.online");
    }
}