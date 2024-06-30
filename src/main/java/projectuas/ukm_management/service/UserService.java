package projectuas.ukm_management.service;

import java.util.List;

import projectuas.ukm_management.data.entity.User;
import projectuas.ukm_management.dto.UserDto;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    User findUserByUsername(String username);

    List<UserDto> findAllUsers();
    
}
