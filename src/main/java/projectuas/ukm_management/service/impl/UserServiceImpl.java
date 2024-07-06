package projectuas.ukm_management.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import projectuas.ukm_management.data.entity.Role;
import projectuas.ukm_management.data.entity.User;
import projectuas.ukm_management.data.repository.RoleRepository;
import projectuas.ukm_management.data.repository.UserRepository;
import projectuas.ukm_management.dto.UserDto;
import projectuas.ukm_management.service.UserService;

// import java.util.Arrays;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
            PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveUser(User user) {
        Role role = roleRepository.findByName("ROLE_ADMIN");

        if (role == null) {
            role = checkRoleExist();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(role));
        // user.setRoles(Arrays.asList(role));
        System.out.println(user);

        userRepository.save(user);
    }

    @Override
    public User update(User updatedUkm, Long id) {
        return userRepository.findById(id).map(ukm -> {
            ukm.setName(updatedUkm.getName());
            ukm.setEmail(updatedUkm.getEmail());
            ukm.setDescription(updatedUkm.getDescription());
            ukm.setVision(updatedUkm.getVision());
            ukm.setMission(updatedUkm.getMission());
            ukm.setLogo(updatedUkm.getLogo());

            return userRepository.save(ukm);
        }).orElse(null);
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
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

    @Override

    public List<User> findAll() {
        return userRepository.findAll();
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
