package auth.repository;

import auth.AuthLoginUserDetails;
import auth.db.AuthLoginDatabase;
import java.util.Optional;

public class AuthLoginRepositoryImpl implements AuthLoginRepository {

    private final AuthLoginDatabase loginUserDatabase;

    public AuthLoginRepositoryImpl(AuthLoginDatabase loginUserDatabase) {
        this.loginUserDatabase = loginUserDatabase;
    }

    @Override
    public Optional<AuthLoginUserDetails> findByUserId(String userId) {
        return loginUserDatabase.findById(userId).map(AuthLoginUserDetails::of);
    }
}
