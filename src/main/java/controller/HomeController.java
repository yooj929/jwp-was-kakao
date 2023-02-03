package controller;

import static utils.response.ResponseBodyUtils.responseBody;
import static utils.response.ResponseHeaderUtils.response200Header;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.MyHeaders;
import utils.request.MyRequest;

public class HomeController implements MyController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Override
    public boolean canHandle(MyRequest myRequest) {
        String path = myRequest.getHeader("path");
        return path.equals("/") || path.equals("/index.html") || path.equals("/favicon.ico");
    }

    @Override
    public void handle(MyRequest myRequest, DataOutputStream dataOutputStream) {
        String path = myRequest.getHeader("path");
        String contentType = myRequest.getHeader("contentType");

        if (path.equals("/favicon.ico") && myRequest.getHeader("method").equals("GET")) {
            getIco(path, contentType, dataOutputStream);
            return;
        }

        if (path.equals("/")) {
            helloWorld(dataOutputStream);
            return;
        }

        if (path.equals("/index.html")) {
            MyHeaders headers = myRequest.getHeaders();
            index(headers, dataOutputStream);
        }
    }

    private void getIco(String path, String contentType, DataOutputStream dataOutputStream) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath("templates" + path);
            response200Header(dataOutputStream, contentType, body.length);
            responseBody(dataOutputStream, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void helloWorld(DataOutputStream dataOutputStream) {
        ;
        byte[] body = "Hello world".getBytes();
        response200Header(dataOutputStream, body.length);
        responseBody(dataOutputStream, body);
    }

    private void index(MyHeaders headers, DataOutputStream dataOutputStream) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath("templates" + headers.get("path"));
            response200Header(dataOutputStream, headers.get("contentType"), body.length);
            responseBody(dataOutputStream, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
