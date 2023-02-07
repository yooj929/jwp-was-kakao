package controller;

import static utils.response.ResponseUtils.make200TemplatesResponse;

import java.io.DataOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import utils.request.MyRequest;

public class IcoController implements MyController{

    private static final Logger logger = LoggerFactory.getLogger(IcoController.class);
    @Override
    public boolean canHandle(MyRequest myRequest) {
        return myRequest.getPath().equals("/favicon.ico") && myRequest.getMethod().equals(HttpMethod.GET);
    }

    @Override
    public void handle(MyRequest myRequest, DataOutputStream dataOutputStream) {
        getIco(myRequest.getPath(), myRequest.getHeader(HttpHeaders.ACCEPT).split(",")[0], dataOutputStream);
    }

    private void getIco(String path, String contentType, DataOutputStream dataOutputStream) {
        make200TemplatesResponse(path, contentType, dataOutputStream, logger);
    }
}
