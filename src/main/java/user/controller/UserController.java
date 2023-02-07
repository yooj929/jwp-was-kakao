package user.controller;

import static user.controller.UserControllerApis.USER_CREATE_API;
import static user.controller.UserControllerApis.USER_FORM_API;
import static user.controller.UserControllerApis.USER_LOGIN_API;
import static user.controller.UserControllerApis.USER_LOGIN_FAIL_API;
import static utils.response.ResponseUtils.make200TemplatesResponse;
import static utils.response.ResponseUtils.make302Response;

import excpetion.NotMatchException;
import infra.controller.BaseMyController;
import java.io.DataOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import user.dto.UserCreateDto;
import user.service.UserService;
import utils.Api;
import utils.request.MyRequest;

public class UserController extends BaseMyController {

    private static final String REDIRECT_INDEX_URL = "/index.html";
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        super(UserControllerApis.values());
        this.userService = userService;
    }

    @Override
    public void handle(MyRequest myRequest, DataOutputStream dataOutputStream) {
        map(myRequest, dataOutputStream);
    }

    private void map(MyRequest myRequest, DataOutputStream dataOutputStream) {
        if (isUserCreate(myRequest.getApi())) {
            createUser(createUserCreateDto(myRequest), dataOutputStream);
            return;
        }
        if (isUserForm(myRequest.getApi())) {
            form(myRequest.getPath(), myRequest.getHeader(HttpHeaders.ACCEPT), dataOutputStream);
            return;
        }
        if (isUserLogin(myRequest.getApi())) {
            loginForm(myRequest.getPath(), myRequest.getHeader(HttpHeaders.ACCEPT), dataOutputStream);
            return;
        }
        if (isUserLoginFailed(myRequest.getApi())){
            loginFail(myRequest.getPath(), myRequest.getHeader(HttpHeaders.ACCEPT), dataOutputStream);
            return;
        }
        throw new NotMatchException("api cannot be match", "api should be matched",
                UserController.class.getSimpleName());
    }

    private void loginForm(String path, String contentType, DataOutputStream dataOutputStream) {
        make200TemplatesResponse(path, contentType, dataOutputStream, logger);
    }

    private void createUser(UserCreateDto userCreateDto, DataOutputStream dataOutputStream) {
        userService.create(userCreateDto);
        make302Response(dataOutputStream, REDIRECT_INDEX_URL, logger);
    }

    private void form(String path, String contentType, DataOutputStream dataOutputStream) {
        make200TemplatesResponse(path, contentType, dataOutputStream, logger);
    }

    private void loginFail(String path, String contentType, DataOutputStream dataOutputStream) {
        make200TemplatesResponse(path, contentType, dataOutputStream, logger);
    }

    private boolean isUserForm(Api api) {
        return USER_FORM_API.getApi().equals(api);
    }

    private boolean isUserCreate(Api api) {
        return USER_CREATE_API.getApi().equals(api);
    }

    private boolean isUserLogin(Api api) {
        return USER_LOGIN_API.getApi().equals(api);
    }

    private boolean isUserLoginFailed(Api api) {
        return USER_LOGIN_FAIL_API.getApi().equals(api);
    }

    private UserCreateDto createUserCreateDto(MyRequest myRequest) {
        return UserCreateDto.builder()
                .userId(myRequest.getParam("userId"))
                .name(myRequest.getParam("name"))
                .email(myRequest.getParam("email"))
                .password(myRequest.getParam("password"))
                .build();
    }
}
