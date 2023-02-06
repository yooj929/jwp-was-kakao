package repository;

import db.DataBase;
import model.User;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public User save(User user) {
        DataBase.addUser(user);
        return user;
    }
}
