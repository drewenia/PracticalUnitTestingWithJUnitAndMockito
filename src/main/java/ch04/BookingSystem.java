package ch04;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class BookingSystem {
    private final Set<LocalTime> setOfHours = new HashSet<>();

    public Set<LocalTime> getSetOfHours() {
        return setOfHours;
    }

    public void bookHour(LocalTime localTime) {
        if (setOfHours.contains(localTime)) {
            throw new IllegalArgumentException("Booking is not allowed for: " + localTime);
        }
        if (localTime.getMinute() != 0) {
            throw new IllegalArgumentException("Booking is allowed only for whole hour.");
        }
        setOfHours.add(localTime);
    }
}
