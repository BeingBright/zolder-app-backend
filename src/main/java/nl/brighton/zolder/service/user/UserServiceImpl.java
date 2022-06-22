package nl.brighton.zolder.service.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.brighton.zolder.model.user.User;
import nl.brighton.zolder.persistance.UserRepository;
import nl.brighton.zolder.service.user.exception.DuplicateUserException;
import nl.brighton.zolder.service.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
@Service
public class UserServiceImpl implements UserService {

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
}
