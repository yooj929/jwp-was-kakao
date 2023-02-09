package auth.dao;

import auth.AuthUserDetailsWithUuid;
import auth.repository.AuthLoginRepository;
import java.util.Optional;

public class BaseAuthLoginDao implements AuthLoginDao {

    private final AuthLoginRepository authLoginRepository;

    public BaseAuthLoginDao(AuthLoginRepository authLoginRepository) {
        this.authLoginRepository = authLoginRepository;
    }

    @Override
    public Optional<AuthUserDetailsWithUuid> findByUserId(String userId) {
        return authLoginRepository.findByUserId(userId);
    }
}
