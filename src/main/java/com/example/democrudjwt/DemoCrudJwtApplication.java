package com.example.democrudjwt;

import com.example.democrudjwt.model.Phones;
import com.example.democrudjwt.model.Profiles;
import com.example.democrudjwt.model.Users;
import com.example.democrudjwt.repository.UsersRepository;
import com.example.democrudjwt.servise.UsersService;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
@Slf4j
public class DemoCrudJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoCrudJwtApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UsersRepository usersRepository, UsersService usersService) {

        return args -> {
            Users user1 = new Users(
                    "firstName",
                    25,
                    "firstUser@google.com"
            );

            Profiles profiles1 = new Profiles(new BigDecimal("100"), user1);
            List<Phones> phones1 = List.of(new Phones("+79099990909", user1));
            user1.setProfiles(profiles1);
            user1.setPhonesList(phones1);

            Users user2 = new Users(
                    "secondName",
                    20,
                    "secondUser@google.com"
            );

            Profiles profiles2 = new Profiles(new BigDecimal("1000"), user2);
            List<Phones> phones2 = List.of(new Phones("+79088880808", user2));
            user2.setProfiles(profiles2);
            user2.setPhonesList(phones2);

            usersRepository.saveAll(List.of(user1, user2));
            usersService.userAccountIncrease(user1);
            usersService.userAccountIncrease(user2);
            generatorRandomAppuser(usersRepository, usersService);
        };
    }

    private void generatorRandomAppuser(UsersRepository usersRepository, UsersService usersService) {
        log.info("generatorRandomAppuser");
        for (int i = 0; i < 10; i++) {
            Faker faker = new Faker();
            String name = faker.name().firstName();
            int age = (int) (Math.random() * 100);
            String email = String.format("%s@codersergg.com", name);
            Users randomUser = new Users(name, age, email);

            Profiles profiles = new Profiles(new BigDecimal((int) (Math.random() * 10000000)), randomUser);

            String number = String.valueOf((int) (Math.random() * 100000000));
            List<Phones> phones = List.of(
                    new Phones("+79" + number + "1", randomUser),
                    new Phones("+79" + number + "2", randomUser));
            randomUser.setProfiles(profiles);
            randomUser.setPhonesList(phones);
            usersRepository.save(randomUser);
            usersService.userAccountIncrease(randomUser);
        }
    }
}
