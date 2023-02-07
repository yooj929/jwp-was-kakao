package businuess.home.controller;

import infra.controller.BaseApis;
import infra.utils.Api;
import org.springframework.http.HttpMethod;

public enum HomeControllerApis implements BaseApis {
    HELLO_WORLD_API(new Api("/", HttpMethod.GET)), INDEX_API(new Api("/index.html", HttpMethod.GET));

    private final Api api;

    HomeControllerApis(Api api) {
        this.api = api;
    }

    public Api getApi() {
        return api;
    }

}
