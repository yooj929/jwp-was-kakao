package statics.controller;

import infra.controller.MyController;
import java.io.DataOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import utils.request.MyRequest;
import utils.response.ResponseUtils;

public class StaticController implements MyController {

    private final Logger logger = LoggerFactory.getLogger(StaticController.class);

    @Override
    public boolean canHandle(MyRequest myRequest) {
        return myRequest.isStatic();
    }

    @Override
    public void handle(MyRequest myRequest, DataOutputStream dataOutputStream) {
        String path = myRequest.getPath();
        String contentType = myRequest.getHeader(HttpHeaders.ACCEPT);
        handleStatic(path, contentType, dataOutputStream);
    }

    private void handleStatic(String path, String contentType, DataOutputStream dataOutputStream){
        ResponseUtils.make200StaticResponse(path, contentType, dataOutputStream, logger);
    }
}
