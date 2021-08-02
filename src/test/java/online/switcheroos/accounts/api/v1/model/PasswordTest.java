package online.switcheroos.accounts.api.v1.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PasswordTest {

    @Test
    @DisplayName("A valid password throws no exception")
    void validPasswordThrowsNoException() {
        Password password = new Password("Test1ng!");
        assertThatNoException().isThrownBy(password::validate);
    }

    @Test
    @DisplayName("A password is able to be hashed successfully with no exception")
    void passwordCanBeHashed() {
        Password password = new Password("Test1ng!");
        assertThatNoException().isThrownBy(password::hash);
        assertThat(password.getValue()).hasSize(60);
    }

    @Test
    @DisplayName("A password throws an exception if there is white space")
    void passwordThrowsExceptionIfThereIsWhiteSpace() {
        Password password = new Password("Test1ng!  ");
        assertThatIllegalArgumentException().isThrownBy(password::validate);
    }

    @Test
    @DisplayName("A password throws an exception if there is no uppercase character")
    void passwordThrowsExceptionIfThereNoUppercase() {
        Password password = new Password("test1ng!");
        assertThatIllegalArgumentException().isThrownBy(password::validate);
    }

    @Test
    @DisplayName("A password throws an exception if there no character digit")
    void passwordThrowsExceptionIfThereIsNoDigit() {
        Password password = new Password("Testing!");
        assertThatIllegalArgumentException().isThrownBy(password::validate);
    }

    @Test
    void passwordThrowsExceptionIfThereIsNoSpecialCharacter() {
        Password password = new Password("Test1ngthepassword");
        assertThatIllegalArgumentException().isThrownBy(password::validate);
    }

    @Test
    void passwordThrowsExceptionIfBelowMinLength() {
        Password password = new Password("Te!1k");
        assertThatIllegalArgumentException().isThrownBy(password::validate);
    }

    @Test
    void passwordThrowsExceptionIfAboveMaxLength() {
        Password password = new Password("sodfXiCsdfPoidfsQdfsdfoisd!sTjisdfjoisdfhoBsd4fojdsfQjio5sdfojisdOfjisdfojifsdojidfsoij");
        assertThatIllegalArgumentException().isThrownBy(password::validate);
    }
}