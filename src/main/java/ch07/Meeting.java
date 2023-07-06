package ch07;

import java.util.Date;

public class Meeting implements Event {
    private final Date startDate;
    private final Date endDate;

    public Meeting(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meeting meeting = (Meeting) o;

        if (!startDate.equals(meeting.startDate)) return false;
        return endDate.equals(meeting.endDate);
    }*/

}
