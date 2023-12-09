package projectuas.streamingPlatform.service;

import projectuas.streamingPlatform.dto.UserDto;
import projectuas.streamingPlatform.data.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    User findUserByUsername(String username);

    List<UserDto> findAllUsers();
}
