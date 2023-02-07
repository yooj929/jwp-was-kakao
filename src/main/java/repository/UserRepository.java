package repository;

import entity.User;

public interface UserRepository {
    User save(User user);
}
