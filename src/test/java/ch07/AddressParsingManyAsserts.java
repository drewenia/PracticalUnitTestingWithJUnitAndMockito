package ch07;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressParsingManyAsserts {
    @Test
    public void testAddressParsing() {
        Address anAddress = new Address("ADDR1$CITY IL 60563$COUNTRY");
        /*assertEquals("ADDR1",  anAddress.getAddr1());
        assertEquals("CITY IL 60563",  anAddress.getCsp());
        assertEquals("COUNTRY",  anAddress.getCountry());*/
    }
}
