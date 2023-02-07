package auth.repository;

import auth.AuthLoginUserDetails;
import java.util.Optional;

public interface AuthLoginRepository {
    Optional<AuthLoginUserDetails> findByUserId(String userId);
}
