package com.example.democrudjwt.servise;

import com.example.democrudjwt.model.Profiles;
import com.example.democrudjwt.model.Users;
import com.example.democrudjwt.repository.ProfilesRepository;
import com.example.democrudjwt.repository.UsersRepository;
import com.example.democrudjwt.util.IncreaseCash;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional(readOnly = true)
public class ProfilesService {

    UsersRepository usersRepository;
    ProfilesRepository profilesRepository;

    public void increaseCash(Users users, IncreaseCash increaseCash) {
        log.info("increaseCash {} {} ", users, increaseCash);
        assert users.getProfiles().getId() != null;
        increaseCash.setProfilesId(users.getProfiles().getId());
        Long profilesId = increaseCash.getProfilesId();
        Optional<Profiles> profilesById = profilesRepository.findById(profilesId);

        increaseCash.setStartCash(profilesById.get().getCash());

        Runnable runnable = () -> {
            try {
                sleepThreadAndIncreaseCashOneStep(increaseCash);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void sleepThreadAndIncreaseCashOneStep(IncreaseCash increaseCash) throws InterruptedException {
        int millis = 20000;
        log.info("sleepThreadAndIncreaseCashOneStep {} milliseconds {} ", millis, increaseCash);
        Thread.sleep(millis);
        increaseCashOneStep(increaseCash);
    }

    @Transactional
    void increaseCashOneStep(IncreaseCash increaseCash) {
        log.info("increaseCashOneStep {} ", increaseCash);
        Optional<Profiles> profiles = profilesRepository.findById(increaseCash.getProfilesId());

        BigDecimal multiply = increaseCash.getStartCash().multiply(new BigDecimal("1.1"));
        int isStartCashGreaterThanMaxCash = multiply.compareTo(increaseCash.getMaxCash());
        if (isStartCashGreaterThanMaxCash > 0) {
            Profiles newProfiles = profiles.get();
            newProfiles.setCash(increaseCash.getMaxCash());
            profilesRepository.save(newProfiles);
        } else {
            profiles.get().setCash(multiply);
            Profiles saveProfiles = profilesRepository.save(profiles.get());
            increaseCash(saveProfiles.getUsersEmail(), increaseCash);
        }
    }
}
