package businuess.user.controller;

import static businuess.user.controller.UserControllerApis.USER_CREATE_API;
import static businuess.user.controller.UserControllerApis.USER_FORM_API;
import static businuess.user.controller.UserControllerApis.USER_LIST_API;
import static businuess.user.controller.UserControllerApis.USER_LIST_HTML_API;
import static businuess.user.controller.UserControllerApis.USER_LOGIN_API;
import static businuess.user.controller.UserControllerApis.USER_LOGIN_FAIL_API;

import infra.utils.Api;

public class UserControllerMapper {
    static boolean isUserForm(Api api) {
        return USER_FORM_API.getApi().equals(api);
    }

    static boolean isUserCreate(Api api) {
        return USER_CREATE_API.getApi().equals(api);
    }

    static boolean isUserLogin(Api api) {
        return USER_LOGIN_API.getApi().equals(api);
    }

    static boolean isUserLoginFailed(Api api) {
        return USER_LOGIN_FAIL_API.getApi().equals(api);
    }

    static boolean isUserList(Api api) {
        return USER_LIST_API.getApi().equals(api) || USER_LIST_HTML_API.getApi().equals(api);
    }

}
