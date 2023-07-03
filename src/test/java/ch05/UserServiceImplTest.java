package ch05;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    @Test
    void assignPasswordShouldSetMD5Hash() {
        //given
        UserDAO userDAO = mock(UserDAO.class);
        ch05.SecurityService securityService = mock(ch05.SecurityService.class);
        User user = mock(User.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl(userDAO, securityService);

        //when
        when(securityService.md5(anyString())).thenReturn("482c811da5d5b4bc6d497ffa98491e38");
        when(user.getPassword()).thenReturn("123");

        userServiceImpl.assignPassword(user);

        //then
        verify(user).setPassword("482c811da5d5b4bc6d497ffa98491e38");
        verify(userDAO).updateUser(user);
    }
}
