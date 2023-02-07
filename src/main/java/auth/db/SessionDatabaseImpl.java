package auth.db;

import auth.AuthUserDetailsWithUuid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class SessionDatabaseImpl implements SessionDatabase {

    Map<String, AuthUserDetailsWithUuid> database = new HashMap<>();
    @Override
    public AuthUserDetailsWithUuid save(AuthUserDetailsWithUuid entity) {
        if(Objects.isNull(entity)){
            return null;
        }
        String uuid = UUID.randomUUID().toString();
        database.put(uuid, entity);
        entity.setUuid(uuid);
        return entity;
    }

    @Override
    public Optional<AuthUserDetailsWithUuid> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public Collection<AuthUserDetailsWithUuid> findAll() {
        return database.values();
    }

    @Override
    public void deleteAll() {
        database.clear();
    }
}
