package config;


import dispatcherservlet.FrontController;

public enum AppConfig {
    ;
    public static FrontController getFrontController() {
        return ControllerConfig.getFrontController();
    }
}
