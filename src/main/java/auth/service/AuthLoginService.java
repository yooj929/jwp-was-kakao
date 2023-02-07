package auth.service;

import auth.dto.AuthLoginUserDto;

public interface AuthLoginService {
    boolean login(AuthLoginUserDto authLoginUserDto);
}
