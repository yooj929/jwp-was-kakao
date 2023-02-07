package home.controller;

import static home.controller.HomeControllerApis.HELLO_WORLD_API;
import static home.controller.HomeControllerApis.INDEX_API;
import static utils.response.ResponseBodyUtils.responseBody;
import static utils.response.ResponseHeaderUtils.response200Header;
import static utils.response.ResponseUtils.make200TemplatesResponse;

import excpetion.NotMatchException;
import infra.controller.BaseMyController;
import java.io.DataOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import utils.Api;
import utils.request.MyRequest;

public class HomeController extends BaseMyController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private static final byte[] HELLO_WORLD_BYTES = "Hello world".getBytes();

    public HomeController() {
        super(HomeControllerApis.values());
    }

    @Override
    public void handle(MyRequest myRequest, DataOutputStream dataOutputStream) {
        map(myRequest, dataOutputStream);
    }

    private void helloWorld(DataOutputStream dataOutputStream) {
        byte[] body = HELLO_WORLD_BYTES;
        response200Header(dataOutputStream, body.length);
        responseBody(dataOutputStream, body);
    }

    private void index(String path, String contentType, DataOutputStream dataOutputStream) {
        make200TemplatesResponse(path, contentType, dataOutputStream, logger);
    }

    private void map(MyRequest myRequest, DataOutputStream dataOutputStream) {
        if (isHelloWorld(myRequest.getApi())) {
            helloWorld(dataOutputStream);
            return;
        }
        if (isIndex(myRequest.getApi())) {
            index(myRequest.getPath(), myRequest.getHeader(HttpHeaders.ACCEPT), dataOutputStream);
            return;
        }
        throw new NotMatchException("api cannot be match", "api should be matched",
                HomeController.class.getSimpleName());
    }

    private boolean isIndex(Api api) {
        return api.equals(INDEX_API.getApi());
    }

    private boolean isHelloWorld(Api api) {
        return api.equals(HELLO_WORLD_API.getApi());
    }

}
