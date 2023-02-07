package user.repository;

import infra.db.Database;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userDatabase.findAll());
    }
}
