package user.dao;

import java.util.List;
import user.User;

public interface UserDao {
    User save(User user);

    List<User> findAll();
}
