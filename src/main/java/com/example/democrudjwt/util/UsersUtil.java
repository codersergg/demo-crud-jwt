package com.example.democrudjwt.util;

import com.example.democrudjwt.dto.UsersDTO;
import com.example.democrudjwt.model.Users;

import java.util.Optional;

public class UsersUtil {

    public static UsersDTO createUsersDTO(Optional<Users> customerOptional) {
        Users users = customerOptional.get();
        return new UsersDTO(
                users.getId(),
                users.getName(),
                users.getAge(),
                users.getEmail());
    }
}
