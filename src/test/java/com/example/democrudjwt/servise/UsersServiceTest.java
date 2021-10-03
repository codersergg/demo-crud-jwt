package com.example.democrudjwt.servise;

import com.example.democrudjwt.auth.ApplicationUserDao;
import com.example.democrudjwt.repository.UsersRepository;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@NoArgsConstructor
class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;
    @Mock
    private ApplicationUserDao applicationUserDao;
    @Mock
    private ProfilesService profilesService;
    private UsersService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UsersService(applicationUserDao, usersRepository, profilesService);
    }

    @Test
    void canGetAllUsers() {
        // when
        underTest.getAllUsers();
        // then
        verify(usersRepository).getAllUsers();
    }

    @Test
    void canDeleteUsers() {
        // when
        underTest.deleteAuthUsers(1L);

        // then
        verify(usersRepository).deleteById(1L);
    }
}