package com.example.democrudjwt;

import com.example.democrudjwt.model.Users;
import com.example.democrudjwt.repository.UsersRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DemoCrudJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoCrudJwtApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UsersRepository usersRepository) {

        return args -> {
            Users admin = new Users(
                    "firstName",
                    25,
                    "firstUser@google.com"

            );
            Users admin2 = new Users(
                    "secondName",
                    20,
                    "secondUser@google.com"
            );
            usersRepository.saveAll(List.of(admin, admin2));

            generatorRandomAppuser(usersRepository);
        };
    }

    private void generatorRandomAppuser(UsersRepository usersRepository) {
        for (int i = 0; i < 10; i++) {
            Faker faker = new Faker();
            String name = faker.name().firstName();
            int age = (int) (Math.random() * 100);
            String email = String.format("%s@codersergg.com", name);
            Users randomUser = new Users(name, age, email);
            usersRepository.save(randomUser);
        }
    }
}
