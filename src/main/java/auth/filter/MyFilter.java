package auth.filter;

import auth.AuthUserDetailsWithUuid;
import auth.db.SessionDatabase;
import java.util.Optional;

public class MyFilter {

    private final SessionDatabase sessionDatabase;

    public MyFilter(SessionDatabase sessionDatabase) {
        this.sessionDatabase = sessionDatabase;
    }
    public Optional<AuthUserDetailsWithUuid> isLogin(String data) {
        String[] cookies = data.split("; ");
        for (String cookie : cookies) {
            if (cookie.startsWith("JSESSIONID")){
                String value = cookie.split("=")[1];
                Optional<AuthUserDetailsWithUuid> byId = sessionDatabase.findById(value);
                if(byId.isPresent()){
                    return byId;
                };
            }
        }
        return Optional.empty();
    }


}
