package businuess.config;

import businuess.statics.controller.StaticController;
import infra.controller.MyController;
import infra.dispatcherservlet.FrontController;

public class ControllerConfig {
    FrontController frontController;

    public ControllerConfig(StaticController staticController, MyController... controllers) {
        this.frontController = new FrontController(staticController, controllers);
    }

    public FrontController getFrontController() {
        return frontController;
    }
}
