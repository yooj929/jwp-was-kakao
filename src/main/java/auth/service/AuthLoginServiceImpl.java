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
    public boolean login(AuthLoginUserDto authLoginUserDto) {
        Optional<AuthLoginUserDetails> authLoginUserDetails = authLoginDao.findByUserId(authLoginUserDto.getUserId());
        return authLoginUserDetails.map(
                loginUserDetails -> loginUserDetails.isCorrectPassword(authLoginUserDto.getPassword())).orElse(false);
    }
}
