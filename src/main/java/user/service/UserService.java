package user.service;

import java.util.List;
import user.User;
import user.dto.UserCreateDto;
import user.dto.UserResponseDto;

public interface UserService {

    User create(UserCreateDto userCreateDto);

    List<UserResponseDto> findAll();
}
