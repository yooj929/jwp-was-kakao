package auth.controller;

import infra.controller.BaseApis;
import infra.utils.Api;
import org.springframework.http.HttpMethod;

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
