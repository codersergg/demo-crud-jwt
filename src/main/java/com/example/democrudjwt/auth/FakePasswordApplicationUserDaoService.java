package com.example.democrudjwt.auth;

import com.example.democrudjwt.model.Users;
import com.example.democrudjwt.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.democrudjwt.security.ApplicationUserRole.AUTHUSER;

@Repository("fake_password")
public class FakePasswordApplicationUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    @Autowired
    public FakePasswordApplicationUserDaoService(PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByEmail(String email) {
        Users users = usersRepository.findByEmailIgnoreCase(email).get();

        ApplicationUser applicationUser = new ApplicationUser(
                users.getName(),
                passwordEncoder.encode("password"),
                AUTHUSER.getGrantedAuthorities(),
                true,
                true,
                true,
                true
        );

        return Optional.of(applicationUser);
    }
}
