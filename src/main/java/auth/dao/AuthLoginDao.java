package auth.dao;

import auth.AuthLoginUserDetails;
import java.util.Optional;

public interface AuthLoginDao {
    Optional<AuthLoginUserDetails> findByUserId(String userId);
}
