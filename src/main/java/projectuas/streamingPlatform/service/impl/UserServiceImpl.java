package projectuas.streamingPlatform.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projectuas.streamingPlatform.data.entity.Role;
import projectuas.streamingPlatform.data.repository.RoleRepository;
import projectuas.streamingPlatform.dto.UserDto;
import projectuas.streamingPlatform.data.entity.User;
import projectuas.streamingPlatform.data.repository.UserRepository;
import projectuas.streamingPlatform.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setIsPremium(false);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");

        if(role == null){
            role = checkRoleExist();
        }
            user.setRoles(Arrays.asList(role));

        userRepository.save(user);
    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    @Override
    public User findUserByEmail(String email) {
        return userService.findUserByEmail(email);
    }


    @Override
    public User findUserByUsername(String username) {
        return userService.findUserByUsername(username);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        return userDto;
    }
}
