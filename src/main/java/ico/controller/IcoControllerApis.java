package ico.controller;

import org.springframework.http.HttpMethod;
import utils.Api;

public enum IcoControllerApis {
    ICO_API(new Api("/favicon.ico", HttpMethod.GET));

    private final Api api;

    IcoControllerApis(Api api) {
        this.api = api;
    }

    public Api getApi() {
        return api;
    }

}
