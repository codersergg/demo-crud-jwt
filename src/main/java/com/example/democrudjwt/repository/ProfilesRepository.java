package com.example.democrudjwt.repository;

import com.example.democrudjwt.model.Profiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ProfilesRepository extends JpaRepository<Profiles, Long> {

}
