package businuess.user.controller;

import static businuess.user.controller.UserControllerConstants.USER_CREATE_URL;
import static businuess.user.controller.UserControllerConstants.USER_FORM_HTML_URL;
import static businuess.user.controller.UserControllerConstants.USER_LIST_HTML_URL;
import static businuess.user.controller.UserControllerConstants.USER_LIST_URL;
import static businuess.user.controller.UserControllerConstants.USER_LOGIN_FAIL_HTML_URL;
import static businuess.user.controller.UserControllerConstants.USER_LOGIN_HTML_URL;

import infra.controller.BaseApis;
import infra.utils.Api;
import org.springframework.http.HttpMethod;

public enum UserControllerApis implements BaseApis {
    USER_FORM_API(new Api(USER_FORM_HTML_URL.url(), HttpMethod.GET)),
    USER_CREATE_API(new Api(USER_CREATE_URL.url(), HttpMethod.POST)),
    USER_LOGIN_API(new Api(USER_LOGIN_HTML_URL.url(), HttpMethod.GET)),
    USER_LOGIN_FAIL_API(new Api(USER_LOGIN_FAIL_HTML_URL.url(), HttpMethod.GET)),
    USER_LIST_API(new Api(USER_LIST_URL.url(), HttpMethod.GET)),
    USER_LIST_HTML_API(new Api(USER_LIST_HTML_URL.url(), HttpMethod.GET)),
    ;
    private final Api api;

    UserControllerApis(Api api) {
        this.api = api;
    }

    public Api getApi() {
        return api;
    }

}

