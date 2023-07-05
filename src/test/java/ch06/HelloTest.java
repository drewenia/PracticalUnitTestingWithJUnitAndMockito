package ch06;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelloTest {
    private static Hello hello;
    private static TimeProvider timeProvider;

    @BeforeAll
    static void setUp() {
        timeProvider = mock(TimeProvider.class);
        hello = new Hello(timeProvider);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11})
    void shouldSayGoodMorningInTheMorning(int hours) {
        when(timeProvider.getTime()).thenReturn(getCalendar(hours));
        assertEquals("good morning",hello.sayHello());
    }

    @ParameterizedTest
    @ValueSource(ints = {12,13,14,15,16,17,18,19,20,21,22,23})
    void shouldSayGoodAfternookInTheAfternoon(int hours){
        when(timeProvider.getTime()).thenReturn(getCalendar(hours));
        assertEquals("good afternoon",hello.sayHello());
    }

    private Calendar getCalendar(int hour){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        return calendar;
    }
}
