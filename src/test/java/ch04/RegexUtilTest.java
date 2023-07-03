package ch04;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class RegexUtilTest {

    private static Stream<Arguments> provideStringArguments() {
        return Stream.of(
                Arguments.of("abc 12", new int[]{}),
                Arguments.of("cdefg 345 12bb12b3", new int[]{345}),
                Arguments.of("asd443 abr123ss 678tt", new int[]{443, 123, 678})
        );
    }

    @ParameterizedTest
    @MethodSource("provideStringArguments")
    void extractNumbersWithThreeOrMoreDigitsFromString(String inputString, int[] expectedNumbers) {
        assertArrayEquals(expectedNumbers, RegexUtil.extractNumbersWithThreeOrMoreDigitsFromString(inputString));
    }
}
