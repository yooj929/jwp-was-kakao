package auth.controller;

import static auth.controller.AuthLoginControllerApis.LOGIN_API;

import infra.utils.request.MyRequest;

public class AuthLoginControllerMapper {
    static boolean isLogin(MyRequest myRequest) {
        return myRequest.getApi().equals(LOGIN_API.getApi());
    }
}
