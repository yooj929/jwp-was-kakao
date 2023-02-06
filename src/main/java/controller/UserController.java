package controller;

import static utils.response.ResponseUtils.make200TemplatesResponse;
import static utils.response.ResponseUtils.make302Response;

import dto.UserCreateDto;
import java.io.DataOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import utils.MyParams;
import utils.request.MyRequest;

public class UserController implements MyController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;

    }


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
        map(myRequest, dataOutputStream, path, method, contentType);
    }

    private void createUser(MyParams params, DataOutputStream dataOutputStream) {
        userService.create(UserCreateDto.builder()
                .userId(params.get("userId"))
                .name(params.get("name"))
                .email(params.get("email"))
                .password(params.get("password"))
                .build());
        make302Response(dataOutputStream, "/index.html", logger);
    }

    private void form(String path, String contentType, DataOutputStream dataOutputStream) {
        make200TemplatesResponse(path, contentType, dataOutputStream, logger);
    }

    private void map(MyRequest myRequest, DataOutputStream dataOutputStream, String path, String method,
                     String contentType) {
        if (path.equals("/user/create") && method.equals("POST")) {
            createUser(myRequest.getParams(), dataOutputStream);
        }

        if (path.equals("/user/form.html") && method.equals("GET")) {
            form(path, contentType, dataOutputStream);
        }
    }
}
