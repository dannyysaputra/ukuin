package projectuas.streamingPlatform.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import projectuas.streamingPlatform.data.entity.User;
import projectuas.streamingPlatform.data.repository.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

//        if (user != null) {
//            return new org.springframework.security.core.userdetails.User(user.getUsername(),
//                    user.getPassword(),
//                    mapRolesToAuthorities(user.getRoles()));
//        }else{
//            throw new UsernameNotFoundException("Invalid username or password.");
//        }
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        } else {
            CustomUser customUser = new CustomUser(user);
            customUser.setRoles(user.getRoles());
            return customUser;
        }
    }
}
