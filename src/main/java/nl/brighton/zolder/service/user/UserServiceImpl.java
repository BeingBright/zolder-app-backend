package nl.brighton.zolder.service.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.brighton.zolder.model.user.User;
import nl.brighton.zolder.persistance.UserRepository;
import nl.brighton.zolder.service.user.exception.DuplicateUserException;
import nl.brighton.zolder.service.user.exception.UserNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String username) throws UserNotFoundException {
        var user = userRepository.getByUsername(username);
        if (user != null) {
            return user;
        }
        throw new UserNotFoundException(username);
    }

    @Override
    public User addUser(User user) throws DuplicateUserException {
        var userInDb = userRepository.getByUsername(user.getUsername());
        if (userInDb == null) {
            return userRepository.save(user);
        } else if (!userInDb.isActive()) {
            userInDb.setActive(true);
            return userRepository.save(userInDb);
        }
        throw new DuplicateUserException(user.getUsername());
    }

    @Override
    public boolean removeUser(User user) throws UserNotFoundException {
        if (userRepository.getById(user.getId()) != null) {
            user.setActive(false);
            userRepository.save(user);
            return true;
        }
        throw new UserNotFoundException(user.getUsername());
    }

    @Override
    public User updateUser(User user) throws UserNotFoundException {
        if (userRepository.getById(user.getId()) != null) {
            return userRepository.save(user);
        }
        throw new UserNotFoundException(user.getUsername());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        try {
            user = getUser(username);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("Invalid user");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isActive(), true, true, true, getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()));
        return authorities;
    }
}
