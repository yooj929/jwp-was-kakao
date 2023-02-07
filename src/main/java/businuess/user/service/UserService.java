package businuess.user.service;

import businuess.user.User;
import java.util.List;
import businuess.user.dto.UserCreateDto;
import businuess.user.dto.UserResponseDto;

public interface UserService {

    User create(UserCreateDto userCreateDto);

    List<UserResponseDto> findAll();
}
