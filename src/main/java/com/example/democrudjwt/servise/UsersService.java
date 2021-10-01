package com.example.democrudjwt.servise;

import com.example.democrudjwt.auth.ApplicationUserDao;
import com.example.democrudjwt.dto.UsersDTO;
import com.example.democrudjwt.exception.IllegalRequestDataException;
import com.example.democrudjwt.model.Users;
import com.example.democrudjwt.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.democrudjwt.util.UsersUtil.createUsersDTO;

@Service
public class UsersService implements UserDetailsService {

    private final ApplicationUserDao applicationUserDao;

    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(@Qualifier("fake_password") ApplicationUserDao applicationUserDao, UsersRepository usersRepository) {
        this.applicationUserDao = applicationUserDao;
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserDao
                .selectApplicationUserByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %s not found", username))
                );
    }

    public List<UsersDTO> getAllUsers() {
        List<Users> usersList = Optional.of(usersRepository.findAll())
                .orElseThrow(() -> new IllegalRequestDataException("No customers found"));
        return usersList.stream()
                .map(customer -> createUsersDTO((Optional.of(customer))))
                .collect(Collectors.toList());
    }
}
