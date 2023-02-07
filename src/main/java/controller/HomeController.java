package controller;

import static utils.response.ResponseBodyUtils.responseBody;
import static utils.response.ResponseHeaderUtils.response200Header;
import static utils.response.ResponseUtils.make200TemplatesResponse;

import java.io.DataOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import utils.request.MyRequest;

public class HomeController implements MyController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Override
    public boolean canHandle(MyRequest myRequest) {
        String path = myRequest.getPath();
        return path.equals("/") || path.equals("/index.html");
    }

    @Override
    public void handle(MyRequest myRequest, DataOutputStream dataOutputStream) {
        String path = myRequest.getPath();
        String contentType = myRequest.getHeader(HttpHeaders.ACCEPT);
        HttpMethod method = myRequest.getMethod();
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

    private void map(DataOutputStream dataOutputStream, String path, String contentType, HttpMethod method) {
        if (isHelloWorld(path, method)) {
            helloWorld(dataOutputStream);
            return;
        }
        if (isIndex(path, method)) {
            index(path, contentType, dataOutputStream);
        }
    }

    private boolean isIndex(String path, HttpMethod method) {
        return path.equals("/index.html") && method.equals(HttpMethod.GET);
    }

    private boolean isHelloWorld(String path, HttpMethod method) {
        return path.equals("/") && method.equals(HttpMethod.GET);
    }

}
