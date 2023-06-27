import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientTest {
    private final Address addressA = new Address("street A");
    private final Address addressB = new Address("street B");
    private static Client client;

    @BeforeAll
    static void setUp() {
        System.out.println("setUp() method called");
        client = new Client();
    }

    @AfterAll
    static void setUpAfter(){
        System.out.println("setUpAfter() method called");
    }

    @Test
    public void afterCreationShouldHaveNoAddress() {
        System.out.println("test called");
        assertEquals(0, client.getAddresses().size());
    }

    @Test
    public void shouldAllowToAddAddress() {
        client.addAddress(addressA);
        assertEquals(1, client.getAddresses().size());
    }

    @Test
    public void shouldAllowToManyAddresses() {
        client.addAddress(addressA);
        client.addAddress(addressB);
        assertEquals(2, client.getAddresses().size());
        assertTrue(client.getAddresses().contains(addressA));
        assertTrue(client.getAddresses().contains(addressB));
    }
}