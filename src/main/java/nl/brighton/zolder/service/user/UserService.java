package nl.brighton.zolder.service.user;

import nl.brighton.zolder.model.user.User;
import nl.brighton.zolder.service.user.exception.DuplicateUserException;
import nl.brighton.zolder.service.user.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUser(String username) throws UserNotFoundException;

    boolean addUser(User user) throws DuplicateUserException;

    boolean removeUser(User user) throws UserNotFoundException;

    boolean updateUser(User user) throws UserNotFoundException;
}
