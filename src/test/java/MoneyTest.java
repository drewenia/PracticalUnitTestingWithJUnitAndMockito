import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTest {

    private static final int VALID_AMOUNT = 5;
    private static final String VALID_CURRENCY = "USD";

    private static Object[] getInvalidAmount() {
        return new Integer[][]{{-12387}, {-5}, {-1}};
    }

    private static Object[] getInvalidCurrency() {
        return new String[][]{{null}, {""}};
    }

    @ParameterizedTest
    @MethodSource("getInvalidAmount")
    public void constructorShouldThrowIllegalArgumentExceptionForInvalidAmount(int invalidAmount) {
        new Money(invalidAmount, VALID_CURRENCY);
    }

    @ParameterizedTest
    @MethodSource("getInvalidCurrency")
    public void constructorShouldThrowIllegalArgumentExceptionForInvalidCurrency(String currency){
        new Money(VALID_AMOUNT,currency);
    }


    private static Object[] getMoney() {
        return new Object[]{
                new Object[]{10, "USD"},
                new Object[]{20, "EUR"}
        };
    }

    @ParameterizedTest
    @MethodSource("getMoney")
    public void constructorShouldSetAmountAndCurrency(int amount, String currency) {
        Money money = new Money(amount, currency);
        assertEquals(amount, money.amount());
        assertEquals(currency, money.currency());
    }

    @Test
    public void shouldThrowExceptions() {
    }
}