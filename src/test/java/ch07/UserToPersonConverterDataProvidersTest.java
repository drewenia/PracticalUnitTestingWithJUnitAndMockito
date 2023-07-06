package ch07;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserToPersonConverterDataProvidersTest {
    private static Object[] getRandomNames() {
        Object[] values = new Object[100][100];
        for (int i = 0; i < values.length; i++) {
            values[i] = new Object[]{RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(8)};
        }
        return values;
    }

    @ParameterizedTest
    @MethodSource("getRandomNames")
    void shouldConvertUserNamesIntoPersonNick(String name, String surname) {
        User user = new User(name, surname);
        Person person = UserToPersonConverter.convert(user);
        assertEquals(name + " " + surname, person.getNick());
    }
}
