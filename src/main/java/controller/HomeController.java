package controller;

import static utils.response.ResponseBodyUtils.responseBody;
import static utils.response.ResponseHeaderUtils.response200Header;
import static utils.response.ResponseUtils.make200TemplatesResponse;

import java.io.DataOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.request.MyRequest;

public class HomeController implements MyController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Override
    public boolean canHandle(MyRequest myRequest) {
        String path = myRequest.getHeader("path");
        return path.equals("/") || path.equals("/index.html");
    }

    @Override
    public void handle(MyRequest myRequest, DataOutputStream dataOutputStream) {
        String path = myRequest.getHeader("path");
        String contentType = myRequest.getHeader("contentType");
        String method = myRequest.getHeader("method");
        map(dataOutputStream, path, contentType, method);
    }


    private void helloWorld(DataOutputStream dataOutputStream) {
        byte[] body = "Hello world".getBytes();
        response200Header(dataOutputStream, body.length);
        responseBody(dataOutputStream, body);
    }

    private void index(String path, String contentType, DataOutputStream dataOutputStream) {
        make200TemplatesResponse(path, contentType, dataOutputStream, logger);
    }

    private void map(DataOutputStream dataOutputStream, String path, String contentType, String method) {
        if (path.equals("/") && method.equals("GET")) {
            helloWorld(dataOutputStream);
            return;
        }
        if (path.equals("/index.html") && method.equals("GET")) {
            index(path, contentType, dataOutputStream);
        }
    }

}
