package auth.filter;

import auth.AuthUserDetails;
import infra.session.SessionManager;
import java.util.Arrays;
import java.util.Objects;

public class MyFilter {

    public static final String JSESSIONID = "JSESSIONID";
    private final SessionManager sessionManager;

    public MyFilter(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public AuthUserDetails isLoginUser(String data) {
        String[] cookies = data.split("; ");
        String jSessionKeyAndValue = findSessionCookie(cookies);
        if (Objects.nonNull(jSessionKeyAndValue)) {
            String key = jSessionKeyAndValue.split("=")[1];
            return (AuthUserDetails) sessionManager.findSession(key).getAttribute(key);
        }
        return null;
    }

    private String findSessionCookie(String[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie ->
                        cookie.startsWith(JSESSIONID))
                .filter(jSessionCookie -> {
                    String value = jSessionCookie.split("=")[1];
                    return Objects.nonNull(sessionManager.findSession(value));
                })
                .findFirst().orElse(null);
    }

}
