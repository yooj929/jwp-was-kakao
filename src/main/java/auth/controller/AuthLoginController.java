package auth.controller;

import static infra.utils.response.ResponseUtils.make302ResponseHeader;
import static infra.utils.response.ResponseUtils.make302ResponseWithCookie;

import auth.AuthUserDetailsWithUuid;
import auth.dto.AuthLoginUserDto;
import auth.service.AuthLoginService;
import infra.controller.BaseMyController;
import infra.utils.request.MyRequest;
import java.io.DataOutputStream;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public void login(AuthLoginUserDto authLoginUserDto, DataOutputStream dataOutputStream) {
        Optional<AuthUserDetailsWithUuid> loginUserDetails = authLoginService.login(authLoginUserDto);
        if (loginUserDetails.isPresent()) {
            MyCookie cookie = new MyCookie(loginUserDetails.get().getUuid());
            make302ResponseWithCookie(dataOutputStream, REDIRECT_INDEX_URL, cookie,logger);
            return;
        }
        make302ResponseHeader(dataOutputStream, REDIRECT_LOGIN_FAIL_URL, logger);
    }

    private AuthLoginUserDto createAuthLoginUserDto(MyRequest myRequest) {
        return new AuthLoginUserDto(myRequest.getParam("userId"), myRequest.getParam("password"));
    }

}
