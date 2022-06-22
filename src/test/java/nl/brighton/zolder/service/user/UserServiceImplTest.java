package nl.brighton.zolder.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.brighton.zolder.dto.User;
import nl.brighton.zolder.dto.types.UserType;
import nl.brighton.zolder.persistance.UserRepository;
import nl.brighton.zolder.service.exception.DuplicateUserException;
import nl.brighton.zolder.service.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserServiceImplTest {

  private UserServiceImpl sut;

  private UserRepository mockedUserRepository;

  @BeforeEach
  void setUp() {
    mockedUserRepository = mock(UserRepository.class);

    when(mockedUserRepository.findAll()).thenReturn(getTemplateUsers());
    when(mockedUserRepository.getByUsername("Present")).thenReturn(new User());
    when(mockedUserRepository.getByUsername("NOT Present")).thenReturn(null);

    sut = new UserServiceImpl(mockedUserRepository);
  }

  @Test
  void getUsers() {
    var result = sut.getUsers();
    assertEquals(getTemplateUsers(), result);
  }

  @Test
  void getUser() throws UserNotFoundException {
    var result = sut.getUser("Present");
    assertEquals(new User(), result);

    assertThrows(UserNotFoundException.class, () -> sut.getUser("NOT Present"));

  }

  @Test
  void addUser() throws DuplicateUserException {
    sut.addUser(new User());
    verify(mockedUserRepository).save(any());
  }

  @Test
  void removeUser() throws UserNotFoundException {
    sut.removeUser(new User("Present", "Present", UserType.ADMIN));
    verify(mockedUserRepository).getByUsername(any());
    verify(mockedUserRepository).delete(any());

    assertThrows(UserNotFoundException.class,
        () -> sut.removeUser(new User("NOT Present", "NOT Present", UserType.ADMIN)));
  }

  @Test
  void updateUser() throws UserNotFoundException {
    sut.updateUser(new User("Present", "Present", UserType.ADMIN));
    verify(mockedUserRepository).getByUsername(any());
    verify(mockedUserRepository).save(any());

    assertThrows(UserNotFoundException.class,
        () -> sut.updateUser(new User("NOT Present", "NOT Present", UserType.ADMIN)));

  }

  private List<User> getTemplateUsers() {
    return new ArrayList<>() {
      {
        add(new User("username1", "username1", UserType.ADMIN));
        add(new User("username2", "username2", UserType.OFFICE));
        add(new User("username3", "username3", UserType.ADMIN));
      }
    };
  }
}