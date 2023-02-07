package auth.config;

import auth.controller.AuthLoginController;
import auth.dao.AuthLoginDao;
import auth.dao.AuthLoginDaoImpl;
import auth.db.AuthLoginDatabase;
import auth.repository.AuthLoginRepository;
import auth.repository.AuthLoginRepositoryImpl;
import auth.service.AuthLoginService;
import auth.service.AuthLoginServiceImpl;
import config.UserConfig;

public class AuthConfig {
    private final AuthLoginController authLoginController;
    private final AuthLoginService authLoginService;
    private final AuthLoginDao authLoginDao;
    private final AuthLoginRepository authLoginRepository;
    private final AuthLoginDatabase authLoginDatabase;

    private AuthConfig(AuthLoginDatabase authLoginDatabase) {
        this.authLoginDatabase = authLoginDatabase;
        this.authLoginRepository = new AuthLoginRepositoryImpl(this.authLoginDatabase);
        this.authLoginDao = new AuthLoginDaoImpl(this.authLoginRepository);
        this.authLoginService = new AuthLoginServiceImpl(this.authLoginDao);
        this.authLoginController = new AuthLoginController(this.authLoginService);
    }

    private static class LazyHolder{
        private static final AuthConfig instance = new AuthConfig(UserConfig.getInstance().getUserDatabase());
    }

    public static AuthConfig getInstance(){
        return LazyHolder.instance;
    }

    public AuthLoginController getAuthLoginController() {
        return authLoginController;
    }

    public AuthLoginService getAuthLoginService() {
        return authLoginService;
    }

    public AuthLoginDao getAuthLoginDao() {
        return authLoginDao;
    }

    public AuthLoginRepository getAuthLoginRepository() {
        return authLoginRepository;
    }

    public AuthLoginDatabase getAuthLoginUserDatabase() {
        return authLoginDatabase;
    }
}
