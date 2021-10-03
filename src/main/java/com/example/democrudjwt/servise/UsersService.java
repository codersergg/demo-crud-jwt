package com.example.democrudjwt.servise;

import com.example.democrudjwt.auth.ApplicationUserDao;
import com.example.democrudjwt.dto.UsersDTO;
import com.example.democrudjwt.exception.IllegalRequestDataException;
import com.example.democrudjwt.model.Phones;
import com.example.democrudjwt.model.Profiles;
import com.example.democrudjwt.model.Users;
import com.example.democrudjwt.repository.UsersRepository;
import com.example.democrudjwt.util.IncreaseCash;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.democrudjwt.util.UsersDTOUtil.createUsersDTO;

@Service
@Slf4j
@Transactional(readOnly = true)
public class UsersService implements UserDetailsService {

    private final ApplicationUserDao applicationUserDao;
    private final UsersRepository usersRepository;
    private final ProfilesService profilesService;

    @Autowired
    public UsersService(@Qualifier(
            "fake_password") ApplicationUserDao applicationUserDao,
                        UsersRepository usersRepository,
                        ProfilesService profilesService) {
        this.applicationUserDao = applicationUserDao;
        this.usersRepository = usersRepository;
        this.profilesService = profilesService;
    }

    @Cacheable("users")
    public Page<UsersDTO> findAll(PageRequest pageRequest) {
        log.info("findAll");
        Page<Users> pageResponse = Optional.of(usersRepository.findAll(pageRequest))
                .orElseThrow(() -> new IllegalRequestDataException("No users found"));

        return new PageImpl<>(pageResponse.stream()
                .map(usersDTO -> createUsersDTO((Optional.of(usersDTO))))
                .collect(Collectors.toList()));
    }

    @Cacheable("users")
    public List<UsersDTO> getAllUsers() {
        log.info("getAllUsers");
        List<Users> usersList = Optional.of(usersRepository.getAllUsers())
                .orElseThrow(() -> new IllegalRequestDataException("No user found"));
        return usersList.stream()
                .map(customer -> createUsersDTO((Optional.of(customer))))
                .collect(Collectors.toList());
    }

    public UsersDTO getUser(Long id) {
        log.info("getUser");
        return createUsersDTO(
                Optional.of(usersRepository.findById(id))
                        .orElseThrow(
                                () -> new IllegalRequestDataException("No user found")));
    }

    @Modifying
    @Transactional
    @CacheEvict(value = "users")
    public void deleteAuthUsers(Long idUser) {
        log.info("deleteAuthUsers");
        assert idUser != null;
        usersRepository.deleteById(idUser);
    }

    @Modifying
    @Transactional
    @CachePut(value = "users")
    public Users register(Users user) {
        log.info("register");
        String userName = user.getName();
        Integer age = user.getAge();
        String email = user.getEmail();
        Users newUser = new Users(userName, age, email);

        Profiles newProfiles = new Profiles(user.getProfiles().getCash(), newUser);

        List<Phones> newPhonesList = user.getPhonesList().stream()
                .map(phones -> new Phones(phones.getValues(), newUser))
                .collect(Collectors.toList());

        newUser.setProfiles(newProfiles);
        newUser.setPhonesList(newPhonesList);
        Users saveUser = usersRepository.save(newUser);

        userAccountIncrease(saveUser);

        return saveUser;
    }

    @Modifying
    @Transactional
    @CachePut(value = "users")
    public Users updateUser(Users user) {
        log.info("updateUser");
        Users foundAuthUser = getUserByEmailIgnoreCase().orElseThrow(
                () -> new IllegalRequestDataException("No user found"));

        assert foundAuthUser.getId() != null;
        if (!foundAuthUser.getId().equals(user.getId())) {
            throw new IllegalRequestDataException("No rights to update user with id " + foundAuthUser.getId());
        }

        Profiles oldProfiles = new Profiles(
                foundAuthUser.getProfiles().getId(),
                foundAuthUser.getProfiles().getCash(),
                foundAuthUser.getProfiles().getUsersEmail()
        );
        user.setProfiles(oldProfiles);

        return usersRepository.save(user);
    }

    public void userAccountIncrease(Users saveUser) {
        log.info("userAccountIncrease");
        Runnable runnable = () -> {
            IncreaseCash increaseCash = new IncreaseCash();
            increaseCash.setMaxCash(saveUser.getProfiles().getCash().multiply(new BigDecimal("2.07")));
            profilesService.increaseCash(saveUser, increaseCash);
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    public Optional<Users> getUserByEmailIgnoreCase() {
        log.info("getUserByEmailIgnoreCase");
        return usersRepository.findByEmailIgnoreCase(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername");
        return applicationUserDao
                .selectApplicationUserByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("User name %s not found", username))
                );
    }
}
