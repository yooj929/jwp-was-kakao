package user.service;

import user.User;
import user.dao.UserDao;
import user.dto.UserCreateDto;

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
