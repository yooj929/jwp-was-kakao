package app.user.dao;

import app.user.User;
import app.user.repository.UserRepository;
import java.util.List;

public class BaseUserDao implements UserDao {

    private final UserRepository userRepository;

    public BaseUserDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
