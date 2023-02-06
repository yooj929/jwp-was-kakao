package service;

import dao.UserDao;
import dto.UserCreateDto;
import model.User;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;

    }

    @Override
    public User create(UserCreateDto userCreateDto) {
        return userDao.save(userCreateDto.toEntity());
    }
}
