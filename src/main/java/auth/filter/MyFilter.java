package auth.filter;

import auth.AuthUserDetails;
import infra.session.Session;
import infra.session.SessionManager;
import java.util.Objects;

public class MyFilter {

    public static final String JSESSIONID = "JSESSIONID";
    private final SessionManager sessionManager;

    public MyFilter(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
    public AuthUserDetails isLoginUser(String data) {
        String[] cookies = data.split("; ");
        for (String cookie : cookies) {
            if (cookie.startsWith(JSESSIONID)){
                String value = cookie.split("=")[1];
                Session session = sessionManager.findSession(value);
                if(Objects.nonNull(session)){
                    return (AuthUserDetails) session.getAttribute(value);
                }
            }
        }
        return null;
    }


}
