package user.controller;

import infra.controller.BaseApis;
import org.springframework.http.HttpMethod;
import utils.Api;

public enum UserControllerApis implements BaseApis {
    USER_FORM_API(new Api("/user/form.html", HttpMethod.GET)),
    USER_CREATE_API(new Api("/user/create", HttpMethod.POST)),
    USER_LOGIN_API(new Api("/user/login.html", HttpMethod.GET)),
    USER_LOGIN_FAIL_API(new Api("/user/login_failed.html", HttpMethod.GET)),
    USER_LIST_API(new Api("/user/list", HttpMethod.GET)),
    USER_LIST_HTML_API(new Api("/user/list", HttpMethod.GET)),
    ;

    private final Api api;

    UserControllerApis(Api api) {
        this.api = api;
    }

    public Api getApi() {
        return api;
    }

}

