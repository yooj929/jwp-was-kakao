package config;


import controller.infra.MyController;
import home.controller.HomeController;
import ico.controller.IcoController;
import infra.dispatcherservlet.FrontController;
import java.util.List;
import statics.controller.StaticController;

public enum ControllerConfig {
    INSTANCE;
    private static final List<MyController> controllers = List.of(
            new HomeController(),
            UserConfig.INSTANCE.getUserController(),
            new StaticController(),
            new IcoController()
    );

    private static final FrontController frontController = new FrontController(controllers);

    public FrontController getFrontController(){
        return frontController;
    }

}
