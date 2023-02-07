package user.repository;

import infra.db.DataBase;
import user.User;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public User save(User user) {
        DataBase.addUser(user);
        return user;
    }
}
