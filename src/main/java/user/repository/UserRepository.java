package user.repository;

import java.util.List;
import user.User;

public interface UserRepository {
    User save(User user);

    List<User> findAll();
}
