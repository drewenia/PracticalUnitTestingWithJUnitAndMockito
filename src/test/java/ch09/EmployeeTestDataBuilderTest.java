package ch09;

import org.junit.jupiter.api.Test;

public class EmployeeTestDataBuilderTest {
    private EmployeeBuilder anEmployee() {
        return new EmployeeBuilder()
                .withFirstName("John")
                .withLastname("Doe")
                .withMobilePhone(new PhoneBuilder().withPhoneNumber("123-456-789").build())
                .withStationaryPhone(new PhoneBuilder().withPhoneNumber("456-789-012").build())
                .withAddress(new AddressBuilder().withAddress("some street").build());
    }

    @Test
    void pmCanDoALot() {
        Employee pmEmpl = anEmployee()
                .withPosition(
                        new PositionBuilder()
                                .withTitle("PM")
                                .start(2010, 1, 1)
                                .end(2011, 7, 3)
                                .build()
                ).build();
    }

    @Test
    void ceoCanDoEverything() {
        Employee ceoEmpl = anEmployee()
                .withPosition(
                        new PositionBuilder()
                                .withTitle("ceo")
                                .start(2011,1,1)
                                .end(2012,5,6)
                                .build()
                ).build();
    }
}
