package user.controller;

import static user.controller.UserControllerApis.USER_CREATE_API;
import static user.controller.UserControllerApis.USER_FORM_API;
import static user.controller.UserControllerApis.USER_LIST_API;
import static user.controller.UserControllerApis.USER_LIST_HTML_API;
import static user.controller.UserControllerApis.USER_LOGIN_API;
import static user.controller.UserControllerApis.USER_LOGIN_FAIL_API;
import static utils.response.ResponseUtils.make200ResponseWithUsersByHandleBars;
import static utils.response.ResponseUtils.make200TemplatesResponse;
import static utils.response.ResponseUtils.make302ResponseHeader;

import excpetion.NotMatchException;
import infra.controller.BaseMyController;
import java.io.DataOutputStream;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import user.User;
import user.dto.UserCreateDto;
import user.dto.UserResponseDto;
import user.service.UserService;
import utils.Api;
import utils.request.MyRequest;

public class UserController extends BaseMyController {

    public static final String REDIRECT_LOGIN_URL = "/user/login.html";
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
        User user = null;
        if (Objects.nonNull(myRequest.getLoginUserDetails())) {
            user = myRequest.getLoginUserDetails().toUser();
        }
        if (isUserCreate(myRequest.getApi())) {
            createUser(user, createUserCreateDto(myRequest), dataOutputStream);
            return;
        }
        if (isUserForm(myRequest.getApi())) {
            form(user, myRequest.getPath(), myRequest.getHeader(HttpHeaders.ACCEPT), dataOutputStream);
            return;
        }
        if (isUserLogin(myRequest.getApi())) {
            loginForm(user, myRequest.getPath(), myRequest.getHeader(HttpHeaders.ACCEPT), dataOutputStream);
            return;
        }
        if (isUserLoginFailed(myRequest.getApi())) {
            loginFail(user, myRequest.getPath(), myRequest.getHeader(HttpHeaders.ACCEPT), dataOutputStream);
            return;
        }
        if (isUserList(myRequest.getApi())) {
            userList(user, myRequest.getPath(), myRequest.getHeader(HttpHeaders.ACCEPT), dataOutputStream);
            return;
        }
        throw new NotMatchException("api cannot be match", "api should be matched",
                UserController.class.getSimpleName());
    }

    private void userList(User user, String path, String contentType, DataOutputStream dataOutputStream) {
        if (Objects.nonNull(user)) {
            List<UserResponseDto> users = userService.findAll();
            make200ResponseWithUsersByHandleBars(contentType, dataOutputStream, users,logger);
            return;
        }
        make302ResponseHeader(dataOutputStream, REDIRECT_LOGIN_URL, logger);


    }

    private void loginForm(User user, String path, String contentType, DataOutputStream dataOutputStream) {
        if (Objects.nonNull(user)) {
            make302ResponseHeader(dataOutputStream, REDIRECT_INDEX_URL, logger);
            return;
        }
        make200TemplatesResponse(path, contentType, dataOutputStream, logger);
    }

    private void createUser(User user, UserCreateDto userCreateDto, DataOutputStream dataOutputStream) {
        if (Objects.nonNull(user)) {
            make302ResponseHeader(dataOutputStream, REDIRECT_INDEX_URL, logger);
            return;
        }
        userService.create(userCreateDto);
        make302ResponseHeader(dataOutputStream, REDIRECT_INDEX_URL, logger);
    }

    private void form(User user, String path, String contentType, DataOutputStream dataOutputStream) {
        if (Objects.nonNull(user)) {
            make302ResponseHeader(dataOutputStream, REDIRECT_INDEX_URL, logger);
            return;
        }
        make200TemplatesResponse(path, contentType, dataOutputStream, logger);
    }

    private void loginFail(User user, String path, String contentType, DataOutputStream dataOutputStream) {
        if (Objects.nonNull(user)) {
            make302ResponseHeader(dataOutputStream, REDIRECT_INDEX_URL, logger);
            return;
        }
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

    private boolean isUserList(Api api) {
        return USER_LIST_API.getApi().equals(api) || USER_LIST_HTML_API.getApi().equals(api);
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
