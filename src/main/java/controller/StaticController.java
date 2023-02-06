package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import utils.request.MyRequest;
import utils.response.ResponseUtils;

import static utils.response.ResponseBodyUtils.responseBody;
import static utils.response.ResponseHeaderUtils.response200Header;

public class StaticController implements MyController{

    private final Logger logger = LoggerFactory.getLogger(StaticController.class);

    @Override
    public boolean canHandle(MyRequest myRequest) {
        return myRequest.isStatic();
    }

    @Override
    public void handle(MyRequest myRequest, DataOutputStream dataOutputStream) {
        String path = myRequest.getHeader("path");
        String contentType = myRequest.getHeader("contentType");

        handleStatic(path, contentType, dataOutputStream);
    }

    private void handleStatic(String path, String contentType, DataOutputStream dataOutputStream){
        ResponseUtils.make200StaticResponse(path, contentType, dataOutputStream, logger);
    }
}
