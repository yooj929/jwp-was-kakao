package auth.db;

import java.util.Optional;
import businuess.user.User;

public interface AuthLoginDatabase {

    Optional<User> findById(String userId);
}
