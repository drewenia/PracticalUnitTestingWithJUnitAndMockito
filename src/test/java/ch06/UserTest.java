package ch06;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    void shouldReturnUsersPhone(){
        CollectionTestUser user = new CollectionTestUser();
        user.addPhone("123 456 789");

        List<String> phones = user.getPhones();

        assertNotNull(phones);
        assertEquals(1,phones.size());
        assertTrue(phones.contains("123 456 789"));
    }
}
