package auth.controller;

import static utils.response.ResponseUtils.make302Response;

import auth.service.AuthLoginService;
import auth.dto.AuthLoginUserDto;
import infra.controller.BaseMyController;
import java.io.DataOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.request.MyRequest;

public class AuthLoginController extends BaseMyController {

    private static final Logger logger = LoggerFactory.getLogger(AuthLoginController.class);
    private final AuthLoginService authLoginService;

    private static final String REDIRECT_INDEX_URL = "/index.html";
    private static final String REDIRECT_LOGIN_FAIL_URL = "/user/login_failed.html";

    public AuthLoginController(AuthLoginService authLoginService) {
        super(AuthLoginControllerApis.values());
        this.authLoginService = authLoginService;
    }

    @Override
    public void handle(MyRequest myRequest, DataOutputStream dataOutputStream) {
        map(myRequest, dataOutputStream);

    }

    public void map(MyRequest myRequest, DataOutputStream dataOutputStream) {
        if (myRequest.getApi().equals(AuthLoginControllerApis.LOGIN_API.getApi())) {
            login(createAuthLoginUserDto(myRequest), dataOutputStream);
        }
    }

    public void login(AuthLoginUserDto authLoginUserDto, DataOutputStream dataOutputStream){
        boolean isSuccess = authLoginService.login(authLoginUserDto);
        if(isSuccess){
            make302Response(dataOutputStream, REDIRECT_INDEX_URL, logger);
            return;
        }
        make302Response(dataOutputStream, REDIRECT_LOGIN_FAIL_URL, logger);
    }

    private AuthLoginUserDto createAuthLoginUserDto(MyRequest myRequest) {
        return new AuthLoginUserDto(myRequest.getParam("userId"), myRequest.getParam("password"));
    }

}
