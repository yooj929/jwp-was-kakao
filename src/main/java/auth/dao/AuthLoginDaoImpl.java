package auth.dao;

import auth.repository.AuthLoginRepository;
import auth.AuthLoginUserDetails;
import java.util.Optional;

public class AuthLoginDaoImpl implements AuthLoginDao {

    private final AuthLoginRepository authLoginRepository;

    public AuthLoginDaoImpl(AuthLoginRepository authLoginRepository) {
        this.authLoginRepository = authLoginRepository;
    }

    @Override
    public Optional<AuthLoginUserDetails> findByUserId(String userId) {
        return authLoginRepository.findByUserId(userId);
    }
}
