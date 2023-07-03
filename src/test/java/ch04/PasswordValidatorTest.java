package ch04;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordValidatorTest {

    @ParameterizedTest(name = "#{index} - Run test with password = {0}")
    @ValueSource(strings = {"Test123@#", "Test121233@#&&"})
    void validateValidPassword(String password) {
        assertTrue(PasswordValidator.validatePassword(password));
    }

    @ParameterizedTest(name = "#{index} - Run test with password = {0}")
    @ValueSource(strings = {"test1234!'^", "test5678%"})
    void validateInvalidPassword(String password) {
        assertFalse(PasswordValidator.validatePassword(password));
    }
}
