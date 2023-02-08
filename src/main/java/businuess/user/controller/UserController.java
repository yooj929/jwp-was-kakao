package businuess.user.controller;

import static businuess.user.controller.UserControllerConstants.INDEX_HTML_URL;
import static businuess.user.controller.UserControllerConstants.LOGIN_HTML_URL;
import static businuess.user.controller.UserControllerConstants.USER_FORM_HTML_URL;
import static businuess.user.controller.UserControllerMapper.isUserCreate;
import static businuess.user.controller.UserControllerMapper.isUserForm;
import static businuess.user.controller.UserControllerMapper.isUserList;
import static businuess.user.controller.UserControllerMapper.isUserLogin;
import static businuess.user.controller.UserControllerMapper.isUserLoginFailed;
import static infra.utils.response.ResponseUtils.make200ResponseUserListView;
import static infra.utils.response.ResponseUtils.make200TemplatesResponse;
import static infra.utils.response.ResponseUtils.make302ResponseHeader;

import businuess.user.dto.UserCreateDto;
import businuess.user.dto.UserResponseDto;
import businuess.user.service.UserService;
import businuess.user.vo.LoginUser;
import excpetion.DuplicateException;
import excpetion.NotMatchException;
import infra.controller.BaseMyController;
import infra.utils.request.MyRequest;
import java.io.DataOutputStream;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

public class UserController extends BaseMyController {
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
        LoginUser user = myRequest.getLoginUser();
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
            userList(user, myRequest.getHeader(HttpHeaders.ACCEPT), dataOutputStream);
            return;
        }
        throw new NotMatchException("api cannot be match", "api should be matched",
                UserController.class.getSimpleName(), myRequest.getApi());
    }

    private void userList(LoginUser user, String contentType, DataOutputStream dataOutputStream) {
        if (Objects.nonNull(user)) {
            List<UserResponseDto> users = userService.findAll();
            make200ResponseUserListView(contentType, dataOutputStream, users, logger);
            return;
        }
        make302ResponseHeader(dataOutputStream, LOGIN_HTML_URL.url(), logger);


    }

    private void loginForm(LoginUser user, String path, String contentType, DataOutputStream dataOutputStream) {
        if (Objects.nonNull(user)) {
            make302ResponseHeader(dataOutputStream, INDEX_HTML_URL.url(), logger);
            return;
        }
        make200TemplatesResponse(path, contentType, dataOutputStream, logger);
    }

    private void createUser(LoginUser user, UserCreateDto userCreateDto, DataOutputStream dataOutputStream) {
        if (Objects.nonNull(user)) {
            make302ResponseHeader(dataOutputStream, INDEX_HTML_URL.url(), logger);
            return;
        }
        try {
            userService.create(userCreateDto);
            make302ResponseHeader(dataOutputStream, INDEX_HTML_URL.url(), logger);
        } catch (DuplicateException e) {
            logger.error(e.getMessage());
            make302ResponseHeader(dataOutputStream, USER_FORM_HTML_URL.url(), logger);
        }
    }

    private void form(LoginUser user, String path, String contentType, DataOutputStream dataOutputStream) {
        if (Objects.nonNull(user)) {
            make302ResponseHeader(dataOutputStream, INDEX_HTML_URL.url(), logger);
            return;
        }
        make200TemplatesResponse(path, contentType, dataOutputStream, logger);
    }

    private void loginFail(LoginUser user, String path, String contentType, DataOutputStream dataOutputStream) {
        if (Objects.nonNull(user)) {
            make302ResponseHeader(dataOutputStream, INDEX_HTML_URL.url(), logger);
            return;
        }
        make200TemplatesResponse(path, contentType, dataOutputStream, logger);
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
