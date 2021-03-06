package com.example.democrudjwt.repository;

import com.example.democrudjwt.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmailIgnoreCase(String toLowerCase);

    @Query("SELECT u FROM Users u")
    List<Users> getAllUsers();
}
