package auth.repository;

import auth.AuthUserDetails;
import auth.AuthUserDetailsWithUuid;
import auth.db.AuthLoginDatabase;
import auth.db.SessionDatabase;
import java.util.Optional;

public class AuthLoginRepositoryImpl implements AuthLoginRepository {

    private final AuthLoginDatabase loginUserDatabase;

    private final SessionDatabase sessionDatabase;

    public AuthLoginRepositoryImpl(AuthLoginDatabase loginUserDatabase, SessionDatabase sessionDatabase) {
        this.loginUserDatabase = loginUserDatabase;
        this.sessionDatabase = sessionDatabase;
    }

    @Override
    public Optional<AuthUserDetailsWithUuid> findByUserId(String userId) {
        Optional<AuthUserDetails> authLoginUserDetail = loginUserDatabase.findById(userId);
        return authLoginUserDetail.map(
                loginUserDetail -> sessionDatabase.save(AuthUserDetailsWithUuid.of(loginUserDetail)));
    }
}
