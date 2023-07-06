package ch07;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressParsingOneAssertTest {
    private final Address address = new Address("ADDR1$CITY IL 60563$COUNTRY");

    @Test
    void testAddr1(){
        //assertEquals("ADDR1",address.getAddr1());
    }

    @Test
    void testCsp(){
        //assertEquals("CITY IL 60563",address.getCsp());
    }

    @Test
    void testCountry(){
        //assertEquals("COUNTRY",address.getCountry());
    }
}
