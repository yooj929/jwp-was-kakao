package config;


import auth.config.AuthConfig;
import user.User;

public class AppConfig {

    private final UserConfig userConfig;
    private final AuthConfig authConfig;
    private final ControllerConfig controllerConfig;

    private AppConfig(){
        userConfig = UserConfig.getInstance();
        authConfig = AuthConfig.getInstance();
        controllerConfig = ControllerConfig.getInstance();
        userConfig.getUserDatabase().save(User.builder()
                .name("abc")
                .userId("a")
                .password("b")
                .email("d@d").build());
    }


    private static class LazyHolder{
        private static final AppConfig instance = new AppConfig();
    }

    public static AppConfig getInstance(){
        return LazyHolder.instance;
    }

    public ControllerConfig getControllerConfig() {
        return controllerConfig;
    }

    public UserConfig getUserConfig() {
        return userConfig;
    }

    public AuthConfig getAuthConfig() {
        return authConfig;
    }
}
