package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.MyHeaders;
import utils.MyParams;
import utils.UserFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import utils.request.MyRequest;
import utils.response.ResponseUtils;

import static db.DataBase.addUser;
import static utils.response.ResponseBodyUtils.responseBody;
import static utils.response.ResponseHeaderUtils.response200Header;
import static utils.response.ResponseHeaderUtils.response302Header;

public class UserController  implements MyController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Override
    public boolean canHandle(MyRequest myRequest) {
        String path = myRequest.getHeaders().get("path");
        return path.startsWith("/user");
    }

    @Override
    public void handle(MyRequest myRequest, DataOutputStream dataOutputStream) {
        String path = myRequest.getHeaders().get("path");
        String method = myRequest.getHeader("method");
        String contentType = myRequest.getHeader("contentType");

        if(path.equals("/user/form.html") && method.equals("POST")){
            createUser(myRequest.getParams(), dataOutputStream);
        }

        if(path.equals("/user/form.html") && method.equals("GET")){
            form(path, contentType, dataOutputStream);
        }
    }

    private void createUser(MyParams params, DataOutputStream dataOutputStream){
        // Memory DB에 유저 데이터 저장
        addUser(UserFactory.createUser(params));
        response302Header(dataOutputStream, "/index.html");
    }

    private void form(String path, String contentType, DataOutputStream dataOutputStream){
        ResponseUtils.make200TemplatesResponse(path, contentType, dataOutputStream, logger);
    }
}
