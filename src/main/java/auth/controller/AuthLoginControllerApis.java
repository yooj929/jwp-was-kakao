package auth.controller;

import infra.controller.BaseApis;
import org.springframework.http.HttpMethod;
import utils.Api;

public enum AuthLoginControllerApis implements BaseApis {
    LOGIN_API(new Api("/user/login", HttpMethod.POST));

    private final Api api;

    AuthLoginControllerApis(Api api) {
        this.api = api;
    }

    public Api getApi() {
        return api;
    }
}
