package com.example.democrudjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UsersDTO {

    private Long id;
    private String name;
    private Integer age;
    private String email;
}
