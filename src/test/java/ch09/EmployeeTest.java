package ch09;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

public class EmployeeTest {
    private static final Phone MOBILE_PHONE = new Phone("123-456-789");
    private static final Phone STATIONARY_PHONE = new Phone("123-456-789");
    private static final Address HOME_ADDRESS = new Address("any street");

    private Employee createEmployee(String name, String surname, Position ceo) {
        return new Employee(name, surname, ceo, HOME_ADDRESS, MOBILE_PHONE, STATIONARY_PHONE);
    }

    @Test
    void ceoCanDoEverything() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, Calendar.MARCH, 1);
        Date startCeo = calendar.getTime();
        calendar.add(Calendar.DATE,1);
        Date endCeo = calendar.getTime();
        Position ceo = new Position("ceo",startCeo,endCeo);
        Employee ceoEmpl = createEmployee("ceoName","ceoSurnam",ceo);
    }

    @Test
    void pmCanDoALot(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022,Calendar.APRIL,11);
        Date startPm = calendar.getTime();
        calendar.add(Calendar.YEAR,3);
        Date endPm = calendar.getTime();
        Position ceo = new Position("pm",startPm,endPm);
        Employee pmEmpl = createEmployee("pmName","pmSurname",ceo);
    }
}
