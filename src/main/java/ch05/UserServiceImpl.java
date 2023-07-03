package ch05;

public class UserServiceImpl {
    private final UserDAO userDAO;
    private final SecurityService securityService;

    public void assignPassword(User user) {
        String passwordMd5 = securityService.md5(user.getPassword());
        user.setPassword(passwordMd5);
        userDAO.updateUser(user);
    }

    public UserServiceImpl(UserDAO dao, SecurityService security) {
        this.userDAO = dao;
        this.securityService = security;
    }
}
