package auth.repository;

import auth.AuthUserDetails;
import auth.AuthUserDetailsWithUuid;
import auth.db.AuthLoginDatabase;
import java.util.Optional;

public class AuthLoginRepositoryImpl implements AuthLoginRepository {

    private final AuthLoginDatabase loginUserDatabase;

    public AuthLoginRepositoryImpl(AuthLoginDatabase loginUserDatabase) {
        this.loginUserDatabase = loginUserDatabase;
    }

    @Override
    public Optional<AuthUserDetailsWithUuid> findByUserId(String userId) {
        Optional<AuthUserDetails> authLoginUserDetail = loginUserDatabase.findById(userId);
        return authLoginUserDetail.map(AuthUserDetailsWithUuid::of);
    }
}
