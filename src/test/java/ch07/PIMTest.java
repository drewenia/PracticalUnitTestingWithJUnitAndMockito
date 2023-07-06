package ch07;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PIMTest {
    private static final int ONE_HOUR = 60;
    private static final Date START_DATE = new Date();
    private static final int MILLIS_IN_MINUTE = 1000 * 60;
    private static final Date END_DATE = new Date(START_DATE.getTime() + ONE_HOUR * MILLIS_IN_MINUTE);

    @Test
    void shouldAddNewEventToCalendar() {
        Calendar calendar = mock(Calendar.class);
        PIM pim = new PIM(calendar);
        ArgumentCaptor<Meeting> argument = ArgumentCaptor.forClass(Meeting.class);

        pim.addMeeting(START_DATE,ONE_HOUR);

        verify(calendar).addEvent(argument.capture());
        Meeting meeting = argument.getValue();
        assertEquals(START_DATE,meeting.getStartDate());
        assertEquals(END_DATE,meeting.getEndDate());
    }
}