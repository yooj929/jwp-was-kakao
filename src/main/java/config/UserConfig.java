package config;

import controller.UserController;
import dao.UserDao;
import dao.UserDaoImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.UserService;
import service.UserServiceImpl;

public enum UserConfig {
    ;
    private static final UserRepository userRepository = new UserRepositoryImpl();
    private static final UserDao userDao = new UserDaoImpl(userRepository);
    private static final UserService userService = new UserServiceImpl(userDao);
    private static final UserController userController = new UserController(userService);

    public static UserController getUserController() {
        return userController;
    }

}
