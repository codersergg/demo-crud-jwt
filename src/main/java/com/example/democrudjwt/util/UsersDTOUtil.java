package com.example.democrudjwt.util;

import com.example.democrudjwt.dto.UsersDTO;
import com.example.democrudjwt.exception.IllegalRequestDataException;
import com.example.democrudjwt.model.Users;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class UsersDTOUtil {

    public static UsersDTO createUsersDTO(Optional<Users> usersOptional) {
        log.info("createUsersDTO ");
        Users users;
        if (usersOptional.isEmpty()) {
            throw new IllegalRequestDataException("No user found");
        }

        users = usersOptional.get();
        return new UsersDTO(
                users.getId(),
                users.getName(),
                users.getAge(),
                users.getEmail(),
                users.getProfiles(),
                users.getPhonesList()
        );
    }
}
