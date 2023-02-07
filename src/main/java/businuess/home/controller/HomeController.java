package businuess.home.controller;

import static businuess.home.controller.HomeControllerConstants.HELLO_WORLD_BYTES;
import static infra.utils.response.ResponseBodyUtils.responseBody;
import static infra.utils.response.ResponseUtils.make200TemplatesResponse;
import static infra.utils.response.ResponseUtils.response200Header;

import excpetion.NotMatchException;
import infra.controller.BaseMyController;
import infra.utils.Api;
import infra.utils.request.MyRequest;
import java.io.DataOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

public class HomeController extends BaseMyController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);


    public HomeController() {
        super(HomeControllerApis.values());
    }

    @Override
    public void handle(MyRequest myRequest, DataOutputStream dataOutputStream) {
        map(myRequest, dataOutputStream);
    }

    private void helloWorld(DataOutputStream dataOutputStream) {
        byte[] body = HELLO_WORLD_BYTES;
        response200Header(dataOutputStream, body.length,logger);
        responseBody(dataOutputStream, body);
    }

    private void index(MyRequest request, DataOutputStream dataOutputStream) {
        make200TemplatesResponse(request.getPath(), request.getHeader(HttpHeaders.ACCEPT), dataOutputStream, logger);
    }

    private void map(MyRequest myRequest, DataOutputStream dataOutputStream) {
        if (isHelloWorld(myRequest.getApi())) {
            helloWorld(dataOutputStream);
            return;
        }
        if (isIndex(myRequest.getApi())) {
            index(myRequest, dataOutputStream);
            return;
        }
        throw new NotMatchException("api cannot be match", "api should be matched",
                HomeController.class.getSimpleName(), myRequest.getApi());
    }

    private boolean isIndex(Api api) {
        return api.equals(HomeControllerApis.INDEX_API.getApi());
    }

    private boolean isHelloWorld(Api api) {
        return api.equals(HomeControllerApis.HELLO_WORLD_API.getApi());
    }

}
