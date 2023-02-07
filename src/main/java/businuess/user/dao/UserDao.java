package businuess.user.dao;

import businuess.user.User;
import java.util.List;

public interface UserDao {
    User save(User user);

    List<User> findAll();
}
