package businuess.config;


import auth.config.AuthConfig;
import businuess.home.controller.HomeController;
import businuess.ico.controller.IcoController;
import infra.dispatcherservlet.FrontController;
import businuess.statics.controller.StaticController;

public class ControllerConfig {

    private final FrontController frontController;

    private ControllerConfig() {
        frontController = new FrontController(
                new HomeController(),
                new IcoController(),
                UserConfig.getInstance().getUserController(),
                AuthConfig.getInstance().getAuthLoginController(),
                new StaticController()
        );
    }

    private static class LazyHolder {
        private static final ControllerConfig instance = new ControllerConfig();
    }

    public static ControllerConfig getInstance() {
        return LazyHolder.instance;
    }


    public FrontController getFrontController() {
        return frontController;
    }

}
