package businuess.user.repository;

import businuess.user.User;
import java.util.List;

public interface UserRepository {
    User save(User user);

    List<User> findAll();
}
