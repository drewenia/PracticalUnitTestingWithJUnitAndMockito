package ch06;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PhoneTest {
    Phone phone = new Phone();

    @Test
    void shouldThrowIllegalArgumentExceptionForEmptyNumber() {
        try {
            phone.setNumber(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException exception) {
            assertEquals("number can not be null or empty", exception.getMessage());
        }
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForPlusPrefixedNumber() {
        try {
            phone.setNumber("+123123");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException exception) {
            assertEquals("number can not be start + sign", exception.getMessage());
        }
    }
}
