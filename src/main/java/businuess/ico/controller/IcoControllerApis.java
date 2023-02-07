package businuess.ico.controller;

import infra.controller.BaseApis;
import infra.utils.Api;
import org.springframework.http.HttpMethod;

public enum IcoControllerApis implements BaseApis {
    ICO_API(new Api("/favicon.ico", HttpMethod.GET));

    private final Api api;

    IcoControllerApis(Api api) {
        this.api = api;
    }

    public Api getApi() {
        return api;
    }

}
