package businuess.user.db;

import auth.AuthUserDetails;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserDatabaseImpl implements UserDatabase{

    private final Map<String, AuthUserDetails> database;

    public UserDatabaseImpl() {
        database = new HashMap<>();
    }

    @Override
    public AuthUserDetails save(AuthUserDetails entity) {
        database.put(entity.getUserId(), entity);
        return entity;
    }

    @Override
    public Optional<AuthUserDetails> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public Collection<AuthUserDetails> findAll() {
        return database.values();
    }

    @Override
    public void deleteAll() {
        database.clear();
    }
}
