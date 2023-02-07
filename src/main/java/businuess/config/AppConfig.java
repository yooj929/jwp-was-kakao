package businuess.config;


import auth.config.AuthConfig;
import businuess.home.controller.HomeController;
import businuess.ico.controller.IcoController;
import businuess.statics.controller.StaticController;
import businuess.user.User;

public class AppConfig {

    private final UserConfig userConfig;
    private final AuthConfig authConfig;
    private final ControllerConfig controllerConfig;

    private AppConfig() {
        userConfig = UserConfig.getInstance();
        authConfig = new AuthConfig(userConfig.getUserDatabase());
        controllerConfig = new ControllerConfig(
                new HomeController(),
                new IcoController(),
                UserConfig.getInstance().getUserController(),
                authConfig.getAuthLoginController(),
                new StaticController());
        postConstruct();
    }

    private void postConstruct() {
        userConfig.getUserDatabase().save(User.builder()
                .name("abc")
                .userId("a")
                .password("b")
                .email("d@d").build());

        userConfig.getUserDatabase().save(User.builder()
                .name("abc")
                .userId("ab")
                .password("b")
                .email("d@d").build());

        userConfig.getUserDatabase().save(User.builder()
                .name("abc")
                .userId("abc")
                .password("b")
                .email("d@d").build());

    }

    private static class LazyHolder {
        private static final AppConfig instance = new AppConfig();
    }

    public static AppConfig getInstance() {
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
