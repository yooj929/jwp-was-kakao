package config;


import auth.config.AuthConfig;

public class AppConfig {

    private final UserConfig userConfig;
    private final AuthConfig authConfig;
    private final ControllerConfig controllerConfig;

    private AppConfig(){
        userConfig = UserConfig.getInstance();
        authConfig = AuthConfig.getInstance();
        controllerConfig = ControllerConfig.getInstance();
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
