package projectuas.ukm_management.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import projectuas.ukm_management.data.entity.Role;
import projectuas.ukm_management.data.entity.User;
import projectuas.ukm_management.data.repository.RoleRepository;
import projectuas.ukm_management.data.repository.UserRepository;
import projectuas.ukm_management.dto.UserDto;
import projectuas.ukm_management.service.UserService;

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

    // @Override
    // public void saveUser(UserDto userDto) {
    //     User user = new User();
    //     user.setName(userDto.getName());
    //     user.setUsername(userDto.getUsername());
    //     user.setEmail(userDto.getEmail());
        // user.setPassword(passwordEncoder.encode(userDto.getPassword()));

    //     Role role = roleRepository.findByName("ROLE_ADMIN");

    //     if (role == null) {
    //         role = checkRoleExist();
    //     }
    //     user.setRoles(Arrays.asList(role));

    //     userRepository.save(user);
    // }

    @Override
    public void saveUser(User user) {
        Role role = roleRepository.findByName("ROLE_ADMIN");

        if (role == null) {
            role = checkRoleExist();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(role));
        System.out.println(user);

        userRepository.save(user);
    }

    private Role checkRoleExist() {
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
        return userRepository.findByName(username);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        // userDto.setUsername(user.getUsername());
        return userDto;
    }
}
