package com.example.democrudjwt.controller;

import com.example.democrudjwt.dto.UsersDTO;
import com.example.democrudjwt.exception.IllegalRequestDataException;
import com.example.democrudjwt.model.Users;
import com.example.democrudjwt.servise.UsersService;
import com.google.common.net.HttpHeaders;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.example.democrudjwt.util.ValidationUtil.checkNew;
import static com.example.democrudjwt.util.ValidationUtil.checkNotFoundWithId;

@RequestMapping(UsersController.URL)
@RestController
@AllArgsConstructor
@Slf4j
public class UsersController {
    static final String URL = "api/v1/user";
    private final UsersService usersService;

    @GetMapping(path = "/page/{pageNumber}/age")
    public Page<UsersDTO> getAllUserByPageSortedByAge(@PathVariable("pageNumber") int page) {
        log.info("getAllUserByPageSortedByAge page {} ", page);
        Sort sortByAge = Sort.by("age").ascending();
        PageRequest pageRequest = PageRequest.of(
                page,
                6,
                sortByAge);
        return usersService.findAll(pageRequest);
    }

    @GetMapping(path = "/page/{pageNumber}/email")
    public Page<UsersDTO> getAllUserByPageSortedByEmail(@PathVariable("pageNumber") int page) {
        log.info("getAllUserByPageSortedByEmail page {} ", page);
        Sort sortByEmail = Sort.by("email").ascending();
        PageRequest pageRequest = PageRequest.of(
                page,
                6,
                sortByEmail);
        return usersService.findAll(pageRequest);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<UsersDTO> getAllUsers() {
        log.info("getAllUsers");
        return usersService.getAllUsers();
    }

    @GetMapping(path = "{userId}")
    public UsersDTO getUser(@PathVariable("userId") Long id) {
        log.info("getUser by id {} ", id);
        checkNotFoundWithId(usersService.getUser(id), id);
        return usersService.getUser(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser() {
        log.info("deleteUser by id=");
        Users foundAuthUser = usersService.getUserByEmailIgnoreCase().orElseThrow(
                () -> new IllegalRequestDataException("No user found"));
        Long idUser = foundAuthUser.getId();
        usersService.deleteAuthUsers(idUser);
    }

    @PostMapping(
            path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Users> register(@Valid @RequestBody Users user) {
        log.info("register {} ", user);
        checkNew(user);
        user = usersService.register(user);
        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(URL)
                .build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(user);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, headers = HttpHeaders.AUTHORIZATION)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Users updateUser(@Valid @RequestBody Users user) {
        log.info("updateUser {} ", user);
        return usersService.updateUser(user);
    }
}
