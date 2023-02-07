package businuess.user.service;

import businuess.user.User;
import businuess.user.dao.UserDao;
import businuess.user.dto.UserCreateDto;
import businuess.user.dto.UserResponseDto;
import java.util.List;
import java.util.stream.Collectors;

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
