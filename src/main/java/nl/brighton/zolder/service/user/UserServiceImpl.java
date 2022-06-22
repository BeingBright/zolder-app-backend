package nl.brighton.zolder.service.user;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.brighton.zolder.dto.User;
import nl.brighton.zolder.persistance.UserRepository;
import nl.brighton.zolder.service.exception.DuplicateUserException;
import nl.brighton.zolder.service.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Setter(AccessLevel.NONE)
@Getter(AccessLevel.NONE)
public class UserServiceImpl implements UserService {

  private final UserRepository repository;

  @Override
  public List<User> getUsers() {
    return repository.findAll();
  }

  @Override
  public User getUser(String username) throws UserNotFoundException {
    User user = repository.getByUsername(username);
    if (user != null) {
      return user;
    }
    throw new UserNotFoundException();
  }

  @Override
  public User addUser(User user) throws DuplicateUserException {
    User userInDb = repository.getByUsername(user.getUsername());
    user.setId(null);
    if (userInDb == null) {
      return repository.save(user);
    }
    throw new DuplicateUserException();
  }

  @Override
  public boolean removeUser(User user) throws UserNotFoundException {
    if (repository.getByUsername(user.getUsername()) != null) {
      repository.delete(user);
      return true;
    }
    throw new UserNotFoundException();
  }

  @Override
  public User updateUser(User user) throws UserNotFoundException {
    if (repository.getByUsername(user.getUsername()) != null) {
      return repository.save(user);
    }
    throw new UserNotFoundException();
  }
}
