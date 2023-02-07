package user.controller;

import org.springframework.http.HttpMethod;
import utils.Api;

public enum UserControllerApis {
    USER_FORM_API(new Api("/user/form.html", HttpMethod.GET)),
    USER_CREATE_API(new Api("/user/create", HttpMethod.POST)),
    USER_LOGIN_API(new Api("/user/login.html", HttpMethod.GET))
    ;

    private final Api api;

    UserControllerApis(Api api) {
        this.api = api;
    }

    public Api getApi() {
        return api;
    }
}
