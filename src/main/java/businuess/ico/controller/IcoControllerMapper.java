package businuess.ico.controller;

import static businuess.ico.controller.IcoControllerApis.ICO_API;

import infra.utils.request.MyRequest;

public class IcoControllerMapper {
    static boolean isIco(MyRequest myRequest) {
        return myRequest.getApi().equals(ICO_API.getApi());
    }
}
