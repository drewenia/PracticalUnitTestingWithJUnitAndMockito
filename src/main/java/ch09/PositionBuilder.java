package ch09;

import java.util.Calendar;
import java.util.Date;

public class PositionBuilder {
    private String title;
    private Date from;
    private Date to;

    public PositionBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public PositionBuilder start(int year, int mount, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, mount, day);
        this.from = calendar.getTime();
        return this;
    }

    public PositionBuilder end(int year, int mount, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,mount,day);
        this.to = calendar.getTime();
        return this;
    }

    public Position build(){
        return new Position(title,from,to);
    }
}
