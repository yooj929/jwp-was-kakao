package infra.db;

import java.util.Collection;
import java.util.Optional;

public interface Database<T> {

     T save(T entity);

    Optional<T> findById(String userId);

    Collection<T> findAll();

    void deleteAll();
}
