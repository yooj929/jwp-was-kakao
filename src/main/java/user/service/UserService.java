package user.service;

import user.User;
import user.dto.UserCreateDto;

public interface UserService {

    User create(UserCreateDto userCreateDto);
}
