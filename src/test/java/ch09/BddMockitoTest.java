package ch09;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class BddMockitoTest {
    private static final int ID_USER = 329847;

    @Test
    void shouldReturnClient(){
        //GIVEN
        User USER = new User();
        UserDAO userDAO = mock(UserDAO.class);
        UserService userService = new UserService(userDAO);
        given(userDAO.getUserById(ID_USER)).willReturn(USER);

        //WHEN
        User user = userService.loadUser(ID_USER);

        //THEN
        assertEquals(USER,user);
    }
}
