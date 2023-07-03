package ch04;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingSystemTest {
    static Set<LocalTime> setOfValidHours;
    private static BookingSystem bookingSystem;
    private final LocalTime LT_19_00 = LocalTime.of(19, 0);
    private final LocalTime LT_18_30 = LocalTime.of(18, 30);

    private static Stream<LocalTime> validTimesForBooking() {
        return Stream.of(
                LocalTime.of(16, 0),
                LocalTime.of(17, 0),
                LocalTime.of(18, 0)
        );
    }

    @BeforeAll
    public static void setUp() {
        bookingSystem = new BookingSystem();
        setOfValidHours = validTimesForBooking().collect(Collectors.toSet());
        setOfValidHours.forEach(item -> bookingSystem.bookHour(item));
    }

    @Test
    void shouldReturnListOfBookedHours() {
        assertEquals(setOfValidHours, bookingSystem.getSetOfHours());
    }


    @Test
    void bookingShouldAddHourToList() {
        bookingSystem.bookHour(LT_19_00);
        assertEquals(4, bookingSystem.getSetOfHours().size());
        assertTrue(bookingSystem.getSetOfHours().contains(LT_19_00));
    }

    @Test
    void shouldNotAllowBookHourTwice() {
        bookingSystem.bookHour(setOfValidHours.iterator().next());
    }

    @Test
    void bookingShouldBeAllowedOnlyForWholeHour() {
        bookingSystem.bookHour(LT_18_30);
    }
}
