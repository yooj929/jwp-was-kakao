package user.service;

import java.util.List;
import java.util.stream.Collectors;
import user.User;
import user.dao.UserDao;
import user.dto.UserCreateDto;
import user.dto.UserResponseDto;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;

    }

    @Override
    public User create(UserCreateDto userCreateDto) {
        return userDao.save(userCreateDto.toEntity());
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userDao.findAll().stream().map(UserResponseDto::of).collect(Collectors.toList());
    }
}
