package ch10;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientTest {
    private final static BigDecimal VALUE_A = new BigDecimal(9);
    private final static BigDecimal VALUE_B = new BigDecimal(2);

    @Test
    void totalValueShouldBeEqualToSumOfAllFundsValues() {
        Client client = new Client();
        Fund fundA = mock(Fund.class);
        Fund fundB = mock(Fund.class);
        when(fundA.getValue()).thenReturn(VALUE_A);
        when(fundB.getValue()).thenReturn(VALUE_B);

        client.addFund(fundA);
        client.addFund(fundB);

        assertEquals(VALUE_A.add(VALUE_B), client.getValueOfAllFunds());
    }
}
