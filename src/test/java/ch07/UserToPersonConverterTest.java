package ch07;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserToPersonConverterTest {
    String name = RandomStringUtils.randomAlphabetic(8);
    String surname = RandomStringUtils.randomAlphabetic(5);
    User user = new User(name,surname);
    Person person = UserToPersonConverter.convert(user);


    @Test
    void name() {
        assertEquals(name + " " + surname,person.getNick());
    }
}
