package ch09;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User loadUser(Integer id){
        return userDAO.getUserById(id);
    }
}
