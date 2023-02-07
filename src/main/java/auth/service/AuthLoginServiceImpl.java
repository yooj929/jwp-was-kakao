package auth.service;

import auth.AuthLoginUserDetails;
import auth.dao.AuthLoginDao;
import auth.dto.AuthLoginUserDto;
import java.util.Optional;

public class AuthLoginServiceImpl implements AuthLoginService {

    private final AuthLoginDao authLoginDao;

    public AuthLoginServiceImpl(AuthLoginDao authLoginDao) {
        this.authLoginDao = authLoginDao;
    }

    @Override
    public Optional<AuthLoginUserDetails> login(AuthLoginUserDto authLoginUserDto) {
        return authLoginDao.findByUserId(authLoginUserDto.getUserId());
    }
}
