package auth.service;

import auth.AuthLoginUserDetails;
import auth.dto.AuthLoginUserDto;
import java.util.Optional;

public interface AuthLoginService {
    Optional<AuthLoginUserDetails> login(AuthLoginUserDto authLoginUserDto);
}
