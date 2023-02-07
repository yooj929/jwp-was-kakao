package auth.config;

import auth.controller.AuthLoginController;
import auth.dao.AuthLoginDao;
import auth.dao.AuthLoginDaoImpl;
import auth.db.AuthLoginDatabase;
import auth.db.SessionDatabase;
import auth.db.SessionDatabaseImpl;
import auth.filter.MyFilter;
import auth.repository.AuthLoginRepository;
import auth.repository.AuthLoginRepositoryImpl;
import auth.service.AuthLoginService;
import auth.service.AuthLoginServiceImpl;
import businuess.config.UserConfig;

public class AuthConfig {
    private final AuthLoginController authLoginController;
    private final AuthLoginService authLoginService;
    private final AuthLoginDao authLoginDao;
    private final AuthLoginRepository authLoginRepository;
    private final AuthLoginDatabase authLoginDatabase;

    private final SessionDatabase sessionDatabase;

    private final MyFilter myFilter;

    private AuthConfig(AuthLoginDatabase authLoginDatabase) {
        this.authLoginDatabase = authLoginDatabase;
        this.sessionDatabase = new SessionDatabaseImpl();
        this.authLoginRepository = new AuthLoginRepositoryImpl(this.authLoginDatabase, this.sessionDatabase);
        this.authLoginDao = new AuthLoginDaoImpl(this.authLoginRepository);
        this.authLoginService = new AuthLoginServiceImpl(this.authLoginDao);
        this.authLoginController = new AuthLoginController(this.authLoginService);
        this.myFilter = new MyFilter(this.sessionDatabase);
    }

    public MyFilter getMyFilter() {
        return myFilter;
    }

    public AuthLoginDatabase getAuthLoginDatabase() {
        return authLoginDatabase;
    }

    public SessionDatabase getSessionDatabase() {
        return sessionDatabase;
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
