package controller;

import static utils.response.ResponseUtils.make200TemplatesResponse;
import static utils.response.ResponseUtils.make302Response;

import dto.UserCreateDto;
import java.io.DataOutputStream;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import service.UserService;
import utils.Api;
import utils.request.MyRequest;

public class UserController extends BaseMyController {

    private static final String REDIRECT_INDEX_URL = "/index.html";
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        super(Arrays.stream(UserControllerApis.values()).map(UserControllerApis::getApi).collect(Collectors.toList()));
        this.userService = userService;
    }

    @Override
    public void handle(MyRequest myRequest, DataOutputStream dataOutputStream) {
        map(myRequest, dataOutputStream);
    }

    private void map(MyRequest myRequest, DataOutputStream dataOutputStream) {
        if (isUserCreate(myRequest.getApi())) {
            createUser(createUserCreateDto(myRequest), dataOutputStream);
        }

        if (isUserForm(myRequest.getApi())) {
            form(myRequest.getPath(), myRequest.getHeader(HttpHeaders.ACCEPT), dataOutputStream);
        }
    }

    private void createUser(UserCreateDto userCreateDto, DataOutputStream dataOutputStream) {
        userService.create(userCreateDto);
        make302Response(dataOutputStream, REDIRECT_INDEX_URL, logger);
    }

    private void form(String path, String contentType, DataOutputStream dataOutputStream) {
        make200TemplatesResponse(path, contentType, dataOutputStream, logger);
    }

    private boolean isUserForm(Api api) {
        return UserControllerApis.USER_FORM_API.getApi().equals(api);
    }

    private boolean isUserCreate(Api api) {
        return UserControllerApis.USER_CREATE_API.getApi().equals(api);
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
