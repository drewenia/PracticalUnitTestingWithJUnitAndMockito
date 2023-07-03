package ch05;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class RaceResultServiceEnchanceTest {
    @Test
    void shouldMessageSetCategory() {
        RaceResultService raceResultService = new RaceResultService();
        Message message = mock(Message.class);
        doReturn("f1").when(message).getCategory("f1");
    }
}
