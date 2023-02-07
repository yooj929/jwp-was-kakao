package config;


import dispatcherservlet.FrontController;

public enum AppConfig {
    INSTANCE;
    public FrontController getFrontController() {
        return ControllerConfig.INSTANCE.getFrontController();
    }
}
