package config;


import infra.dispatcherservlet.FrontController;

public enum AppConfig {
    INSTANCE;
    public FrontController getFrontController() {
        return ControllerConfig.INSTANCE.getFrontController();
    }
}
