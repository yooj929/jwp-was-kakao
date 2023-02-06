package controller;

import static utils.response.ResponseUtils.make200TemplatesResponse;

import java.io.DataOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.request.MyRequest;

public class IcoController implements MyController{

    private static final Logger logger = LoggerFactory.getLogger(IcoController.class);
    @Override
    public boolean canHandle(MyRequest myRequest) {
        return myRequest.getHeader("path").equals("/favicon.ico");
    }

    @Override
    public void handle(MyRequest myRequest, DataOutputStream dataOutputStream) {
        getIco(myRequest.getHeader("path"), myRequest.getHeader("contentType"), dataOutputStream);
    }

    private void getIco(String path, String contentType, DataOutputStream dataOutputStream) {
        make200TemplatesResponse(path, contentType, dataOutputStream, logger);
    }
}
