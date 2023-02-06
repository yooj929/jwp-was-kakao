package service;

import dto.UserCreateDto;
import model.User;

public interface UserService {

    User create(UserCreateDto userCreateDto);
}
