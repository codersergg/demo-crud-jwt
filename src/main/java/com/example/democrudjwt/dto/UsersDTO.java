package com.example.democrudjwt.dto;

import com.example.democrudjwt.model.Phones;
import com.example.democrudjwt.model.Profiles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UsersDTO {

    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Profiles profiles;
    private List<Phones> phones;
}
