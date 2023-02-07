package user.db;

import auth.db.AuthLoginDatabase;
import infra.db.Database;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import user.User;

public class UserDatabase implements Database<User>, AuthLoginDatabase {

    private final Map<String, User> database;

    public UserDatabase() {
        database = new HashMap<>();
    }
    @Override
    public User save(User entity) {
        database.put(entity.getUserId(), entity);
        return entity;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }
    @Override
    public Collection<User> findAll() {
        return database.values();
    }

    @Override
    public void deleteAll() {
        database.clear();
    }
}
