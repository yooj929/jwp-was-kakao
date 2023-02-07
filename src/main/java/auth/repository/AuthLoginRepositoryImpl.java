package auth.repository;

import auth.AuthLoginUserDetails;
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
    public Optional<AuthLoginUserDetails> findByUserId(String userId) {
        Optional<AuthLoginUserDetails> authLoginUserDetails = loginUserDatabase.findById(userId)
                .map(AuthLoginUserDetails::of);
        return Optional.ofNullable(sessionDatabase.save(authLoginUserDetails.orElse(null)));
    }
}
