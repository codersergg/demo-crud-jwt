package com.example.democrudjwt.controller;

import com.example.democrudjwt.servise.UsersService;
import com.example.democrudjwt.dto.UsersDTO;
import com.example.democrudjwt.repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(UsersController.URL)
@RestController
@AllArgsConstructor
@Slf4j
public class UsersController {
    static final String URL = "api/v1/account";
    private final UsersService usersService;
    private final UsersRepository usersRepository;
    UsersService applicationUserService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('AUTHUSER')")
    public @ResponseBody List<UsersDTO> getAllUsers() {
        log.info("Get All Users ");
        return applicationUserService.getAllUsers();
    }
}
