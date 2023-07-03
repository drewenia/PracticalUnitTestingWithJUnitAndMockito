package ch03;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FahrenheitCelciusConverterTest {
    private static Object[] getValues() {
        return new Object[]{
                new Object[]{32, 0},
                new Object[]{99, 37},
                new Object[]{212, 100}
        };
    }

    @ParameterizedTest
    @MethodSource("getValues")
    void shouldConvertCelciusToFahrenheit(int fahrenheit, int celcius) {
        assertEquals(fahrenheit, FahrenheitCelciusConverter.toFahrenheit(celcius));
    }

    @ParameterizedTest
    @MethodSource("getValues")
    void shouldConvertFahrenheitToCelcius(int fahrenheit, int celcius) {
        assertEquals(celcius, FahrenheitCelciusConverter.toCelcius(fahrenheit));
    }
}
