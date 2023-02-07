package businuess.config;

import businuess.user.controller.UserController;
import businuess.user.dao.UserDao;
import businuess.user.dao.UserDaoImpl;
import businuess.user.db.UserDatabase;
import businuess.user.repository.UserRepository;
import businuess.user.repository.UserRepositoryImpl;
import businuess.user.service.UserService;
import businuess.user.service.UserServiceImpl;

public class UserConfig {

    private final UserDatabase userDatabase;
    private final UserRepository userRepository;
    private final UserDao userDao;
    private final UserService userService;
    private final UserController userController;

    private UserConfig() {
        userDatabase = new UserDatabase();
        userRepository = new UserRepositoryImpl(userDatabase);
        userDao = new UserDaoImpl(userRepository);
        userService = new UserServiceImpl(userDao);
        userController = new UserController(userService);
    }

    private static class LazyHolder {
        private static final UserConfig instance = new UserConfig();

    }

    public static UserConfig getInstance() {
        return LazyHolder.instance;
    }

    public UserController getUserController() {
        return userController;
    }

    public UserService getUserService() {
        return userService;
    }

    public UserDatabase getUserDatabase() {
        return userDatabase;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
