package businuess.user.service;

import businuess.user.User;
import businuess.user.dto.UserCreateDto;
import businuess.user.dto.UserResponseDto;
import java.util.List;

public interface UserService {

    User create(UserCreateDto userCreateDto);

    List<UserResponseDto> findAll();
}
