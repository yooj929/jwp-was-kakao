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
        String path = myRequest.getHeader("path");
        return path.startsWith("/user");
    }

    @Override
    public void handle(MyRequest myRequest, DataOutputStream dataOutputStream) {
        String path = myRequest.getHeader("path");
        String method = myRequest.getHeader("method");
        String contentType = myRequest.getHeader("contentType");
        map(myRequest, dataOutputStream, path, method, contentType);
    }

    private void map(MyRequest myRequest, DataOutputStream dataOutputStream, String path, String method,
                     String contentType) {
        if (isUserCreate(path, method)) {
            createUser(UserCreateDto.builder()
                    .userId(myRequest.getParam("userId"))
                    .name(myRequest.getParam("name"))
                    .email(myRequest.getParam("email"))
                    .password(myRequest.getParam("password"))
                    .build(), dataOutputStream);
        }

        if (isUserForm(path, method)) {
            form(path, contentType, dataOutputStream);
        }
    }

    private void createUser(UserCreateDto userCreateDto, DataOutputStream dataOutputStream) {
        userService.create(userCreateDto);
        make302Response(dataOutputStream, "/index.html", logger);
    }

    private void form(String path, String contentType, DataOutputStream dataOutputStream) {
        make200TemplatesResponse(path, contentType, dataOutputStream, logger);
    }

    private boolean isUserForm(String path, String method) {
        return path.equals("/user/form.html") && method.equals("GET");
    }

    private boolean isUserCreate(String path, String method) {
        return path.equals("/user/create") && method.equals("POST");
    }
}
