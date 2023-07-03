package ch03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilTest {
    public static Object[] getVerbs() {
        return new String[][]{{"deneme"}, {"markdown"}};
    }

    @ParameterizedTest
    @MethodSource("getVerbs")
    public void reverseVerb(String verbs) {
        String reversedString = new StringBuilder(verbs).reverse().toString();
        assertEquals(reversedString, StringUtil.reverse(verbs));
    }

    @Test
    public void reverseShouldThrowNullPointerException() {
        StringUtil.reverse(null);
    }
}
