package businuess.home.controller;

import static businuess.home.controller.HomeControllerApis.HELLO_WORLD_API;
import static businuess.home.controller.HomeControllerApis.INDEX_API;

import infra.utils.Api;

public class HomeControllerMapper {

    static boolean isIndex(Api api) {
        return api.equals(INDEX_API.getApi());
    }

    static boolean isHelloWorld(Api api) {
        return api.equals(HELLO_WORLD_API.getApi());
    }
}
