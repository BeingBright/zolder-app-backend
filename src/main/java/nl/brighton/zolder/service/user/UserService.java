package nl.brighton.zolder.service.user;

import java.util.List;
import nl.brighton.zolder.dto.User;
import nl.brighton.zolder.service.exception.DuplicateUserException;
import nl.brighton.zolder.service.exception.UserNotFoundException;

public interface UserService {

  List<User> getUsers();

  User getUser(String username) throws UserNotFoundException;

  User addUser(User user) throws DuplicateUserException;

  boolean removeUser(User user) throws UserNotFoundException;

  User updateUser(User user) throws UserNotFoundException;


}
