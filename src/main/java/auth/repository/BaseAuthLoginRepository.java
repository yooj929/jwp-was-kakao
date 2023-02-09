package auth.repository;

import auth.AuthUserDetails;
import auth.AuthUserDetailsWithUuid;
import auth.db.AuthLoginDatabase;
import java.util.Optional;

public class BaseAuthLoginRepository implements AuthLoginRepository {

    private final AuthLoginDatabase authLoginDatabase;

    public BaseAuthLoginRepository(AuthLoginDatabase authLoginDatabase) {
        this.authLoginDatabase = authLoginDatabase;
    }

    @Override
    public Optional<AuthUserDetailsWithUuid> findByUserId(String userId) {
        Optional<AuthUserDetails> authLoginUserDetail = authLoginDatabase.findById(userId);
        return authLoginUserDetail.map(AuthUserDetailsWithUuid::of);
    }
}
