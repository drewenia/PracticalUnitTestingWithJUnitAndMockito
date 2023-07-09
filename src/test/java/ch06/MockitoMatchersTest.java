package ch06;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class MockitoMatchersTest {
    UserDAO userDAO = mock(UserDAO.class);
    User user = new User();

    @Test
    void testWithMockitoMatchers() {
        when(userDAO.getUser(anyInt())).thenReturn(user);

        assertEquals(user, userDAO.getUser(1));
        assertEquals(user, userDAO.getUser(2));
        assertEquals(user, userDAO.getUser(3));

        verify(userDAO,times(3)).getUser(anyInt());
    }
}
