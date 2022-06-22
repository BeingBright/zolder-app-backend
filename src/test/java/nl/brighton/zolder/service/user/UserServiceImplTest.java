package nl.brighton.zolder.service.user;

import nl.brighton.zolder.model.user.User;
import nl.brighton.zolder.model.user.UserRoleType;
import nl.brighton.zolder.persistance.UserRepository;
import nl.brighton.zolder.service.user.exception.DuplicateUserException;
import nl.brighton.zolder.service.user.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository mockedUserRepository;
    private UserServiceImpl sut;

    @BeforeEach
    void setUp() {
        mockedUserRepository = mock(UserRepository.class);

        when(mockedUserRepository.getByUsername("Active User")).thenReturn(baseUser(true));
        when(mockedUserRepository.getByUsername("InActive User")).thenReturn(alterUser(false));
        when(mockedUserRepository.getById("id")).thenReturn(baseUser());
        when(mockedUserRepository.findAll()).thenReturn(getUserList());
        when(mockedUserRepository.save(baseUser(false))).thenReturn(baseUser(false));
        when(mockedUserRepository.save(baseUser(true))).thenReturn(baseUser(true));
        when(mockedUserRepository.save(alterUser(true))).thenReturn(alterUser(true));
        when(mockedUserRepository.save(alterUser(true))).thenReturn(alterUser(true));
        when(mockedUserRepository.save(new User())).thenReturn(new User());

        when(mockedUserRepository.save(alterUser())).thenReturn(alterUser());

        sut = new UserServiceImpl(mockedUserRepository);
    }

    @Test
    void getUsers() {

        var result = sut.getUsers();
        assertEquals(getUserList(), result);
        verify(mockedUserRepository).findAll();

    }

    @Test
    void getUser() throws UserNotFoundException {

        var result = sut.getUser(baseUser().getUsername());
        assertEquals(baseUser(), result);
        verify(mockedUserRepository).getByUsername(baseUser().getUsername());

        assertThrows(UserNotFoundException.class, () -> sut.getUser("NON"));
    }

    @Test
    void addUser() throws DuplicateUserException {

        var result = sut.addUser(new User());
        assertEquals(new User(), result);

        verify(mockedUserRepository).getByUsername(null);
        verify(mockedUserRepository).save(new User());

        result = sut.addUser(alterUser(false));
        assertEquals(alterUser(), result);
        verify(mockedUserRepository).getByUsername(alterUser().getUsername());
        verify(mockedUserRepository).save(alterUser(true));

        assertThrows(DuplicateUserException.class, () -> sut.addUser(baseUser()));

    }

    @Test
    void removeUser() throws UserNotFoundException {

        assertTrue(sut.removeUser(baseUser(true)));
        verify(mockedUserRepository).save(baseUser(false));

        assertThrows(UserNotFoundException.class, () -> sut.removeUser(new User()));
    }

    @Test
    void updateUser() throws UserNotFoundException {

        var result = sut.updateUser(baseUser(true));
        assertEquals(baseUser(), result);

        assertThrows(UserNotFoundException.class, () -> sut.updateUser(new User()));

    }

    private User baseUser() {
        return new User("id", "Active User", "Password", true, UserRoleType.WORKER);
    }

    private User baseUser(boolean isActive) {
        return new User("id", "Active User", "Password", isActive, UserRoleType.WORKER);
    }

    private User alterUser() {
        return new User("id", "InActive User", "Password", false, UserRoleType.WORKER);
    }

    private User alterUser(boolean isActive) {
        return new User("id", "InActive User", "Password", isActive, UserRoleType.WORKER);
    }

    private ArrayList<User> getUserList() {
        return new ArrayList<>(Arrays.asList(baseUser(), new User()));
    }

}