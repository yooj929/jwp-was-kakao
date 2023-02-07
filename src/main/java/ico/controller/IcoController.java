package ico.controller;

import static ico.controller.IcoControllerApis.ICO_API;
import static utils.response.ResponseUtils.make200TemplatesResponse;

import controller.infra.BaseMyController;
import excpetion.NotMatchException;
import java.io.DataOutputStream;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import user.controller.UserController;
import utils.request.MyRequest;

public class IcoController extends BaseMyController {

    private static final Logger logger = LoggerFactory.getLogger(IcoController.class);

    public IcoController() {
        super(Arrays.stream(IcoControllerApis.values())
                .map(IcoControllerApis::getApi)
                .collect(Collectors.toList()));
    }

    @Override
    public void handle(MyRequest myRequest, DataOutputStream dataOutputStream) {
        if (myRequest.getApi().equals(ICO_API.getApi())) {
            getIco(myRequest.getPath(), myRequest.getHeader(HttpHeaders.ACCEPT).split(",")[0], dataOutputStream);
        }
        throw new NotMatchException("api cannot be match", "api should be matched",
                IcoController.class.getSimpleName());
    }
    private void getIco(String path, String contentType, DataOutputStream dataOutputStream) {
        make200TemplatesResponse(path, contentType, dataOutputStream, logger);
    }
}
