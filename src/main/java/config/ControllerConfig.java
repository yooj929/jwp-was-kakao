package config;

import static config.UserConfig.getUserController;

import controller.HomeController;
import controller.IcoController;
import controller.MyController;
import controller.StaticController;
import dispatcherservlet.FrontController;
import java.util.List;

public enum ControllerConfig {
    ;
    private static final List<MyController> controllers = List.of(
            new HomeController(),
            getUserController(),
            new StaticController(),
            new IcoController()
    );

    private static final FrontController frontController = new FrontController(controllers);

    public static FrontController getFrontController(){
        return frontController;
    }

}
