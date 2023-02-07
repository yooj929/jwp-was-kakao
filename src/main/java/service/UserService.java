package service;

import dto.UserCreateDto;
import entity.User;

public interface UserService {

    User create(UserCreateDto userCreateDto);
}
