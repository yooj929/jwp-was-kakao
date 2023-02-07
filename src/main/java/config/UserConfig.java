package config;

import user.controller.UserController;
import user.dao.UserDao;
import user.dao.UserDaoImpl;
import user.repository.UserRepository;
import user.repository.UserRepositoryImpl;
import user.service.UserService;
import user.service.UserServiceImpl;

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
