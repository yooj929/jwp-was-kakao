package businuess.home.controller;

import static businuess.home.controller.HomeControllerConstants.HELLO_WORLD_URL;
import static businuess.home.controller.HomeControllerConstants.INDEX_HTML_URL;

import infra.controller.BaseApis;
import infra.utils.Api;
import org.springframework.http.HttpMethod;

public enum HomeControllerApis implements BaseApis {
    HELLO_WORLD_API(new Api(HELLO_WORLD_URL, HttpMethod.GET)), INDEX_API(new Api(INDEX_HTML_URL, HttpMethod.GET));

    private final Api api;

    HomeControllerApis(Api api) {
        this.api = api;
    }

    public Api getApi() {
        return api;
    }

}
