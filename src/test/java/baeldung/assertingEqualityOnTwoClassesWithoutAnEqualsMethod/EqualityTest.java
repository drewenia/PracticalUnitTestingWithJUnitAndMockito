package baeldung.assertingEqualityOnTwoClassesWithoutAnEqualsMethod;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EqualityTest {

    @Test
    void shouldClassEquals() {
        Person expected = new Person(1L, "Jane", "Doe");
        Address address1 = new Address(1L, "New York", "Sesame Street", "United States");
        expected.setAddress(address1);

        Person actual = new Person(1L, "Jane", "Doe");
        Address address2 = new Address(1L, "New York", "Sesame Street", "United States");
        actual.setAddress(address2);

        assertTrue(new ReflectionEquals(expected, "address").matches(actual));
        assertTrue(new ReflectionEquals(expected.getAddress()).matches(actual.getAddress()));
    }
}
