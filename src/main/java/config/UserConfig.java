package config;

import controller.UserController;
import dao.UserDao;
import dao.UserDaoImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.UserService;
import service.UserServiceImpl;

public enum UserConfig {
    INSTANCE;
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final UserDao userDao = new UserDaoImpl(userRepository);
    private final UserService userService = new UserServiceImpl(userDao);
    private final UserController userController = new UserController(userService);

    public UserController getUserController() {
        return userController;
    }

    public UserService getUserService() {
        return userService;
    }

}
