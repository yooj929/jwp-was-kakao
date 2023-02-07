package auth.controller;

import static auth.controller.AuthLoginControllerConstants.USER_LOGIN_URL;

import infra.controller.BaseApis;
import infra.utils.Api;
import org.springframework.http.HttpMethod;

public enum AuthLoginControllerApis implements BaseApis {
    LOGIN_API(new Api(USER_LOGIN_URL.url(), HttpMethod.POST));

    private final Api api;

    AuthLoginControllerApis(Api api) {
        this.api = api;
    }

    public Api getApi() {
        return api;
    }
}
