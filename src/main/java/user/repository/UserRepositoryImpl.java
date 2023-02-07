package user.repository;

import infra.db.Database;
import user.User;

public class UserRepositoryImpl implements UserRepository {

    private final Database<User> userDatabase;

    public UserRepositoryImpl(Database<User> userDatabase) {
        this.userDatabase = userDatabase;
    }

    @Override
    public User save(User user) {
        return userDatabase.save(user);
    }
}
