package businuess.config;


import infra.controller.MyController;
import infra.dispatcherservlet.FrontController;

public class ControllerConfig {

    private final FrontController frontController;

    ControllerConfig(MyController... myControllers) {
        frontController = new FrontController(myControllers);
    }

    public FrontController getFrontController() {
        return frontController;
    }

}
