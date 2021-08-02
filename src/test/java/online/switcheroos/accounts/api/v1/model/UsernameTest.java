package online.switcheroos.accounts.api.v1.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UsernameTest {

    @Test
    @DisplayName("Username is able to be changed/set")
    void usernameIsAbleToBeSet() {
        Username username = new Username("daniel");
        username.setValue("jimmy");

        assertThat(username.getValue()).isEqualTo("jimmy");
    }

    @Test
    @DisplayName("Username throws Exception when length is < MIN-LENGTH")
    void usernameThrowsExceptionWhenTheValueLengthIsTooSmall() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Username("ab"));
    }

    @Test
    @DisplayName("Username throws Exception when length is > MAX-LENGTH")
    void usernameThrowsExceptionWhenTheValueLengthIsTooLarge() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Username("abcdefghijklmopq"));
    }

    @Test
    @DisplayName("Username throws Exception when the username is null")
    void usernameThrowsExceptionWhenTheValueIsNull() {
        assertThatNullPointerException().isThrownBy(() -> new Username(null));
    }

    @Test
    @DisplayName("Username throws Exception when the username is empty (length of 0)")
    void usernameThrowsExceptionWhenValueIsEmpty() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Username(""));
    }

    @Test
    @DisplayName("Username throws Exception when the username is just blank (empty spaces)")
    void usernameThrowsExceptionWhenValueIsBlankSpaces() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Username("                  "));
    }

    @Test
    @DisplayName("Username throws Exception when the username has an illegal character")
    void usernameThrowsExceptionWhenValueIncludesIllegalCharacter() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Username("d@niel"));
    }

    @Test
    @DisplayName("Username trims whitespace around the value given")
    void usernameRemovesWhiteSpaceFromValue() {
        Username username = new Username("                    daniel                     ");
        assertThat(username.getValue()).isEqualTo("daniel");
    }
}