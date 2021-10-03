package com.example.democrudjwt.servise;

import com.example.democrudjwt.model.Profiles;
import com.example.democrudjwt.model.Users;
import com.example.democrudjwt.repository.ProfilesRepository;
import com.example.democrudjwt.repository.UsersRepository;
import com.example.democrudjwt.util.IncreaseCash;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ProfilesService {

    UsersRepository usersRepository;
    ProfilesRepository profilesRepository;

    public void incCash(Users users, IncreaseCash increaseCash) {

        assert users.getProfiles().getId() != null;
        increaseCash.setProfilesId(users.getProfiles().getId());
        Long profilesId = increaseCash.getProfilesId();
        Optional<Profiles> profilesById = profilesRepository.findById(profilesId);

        increaseCash.setStartCash(profilesById.get().getCash());

        Runnable runnable = () -> {
            try {
                increaseCashOneStep(increaseCash);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void increaseCashOneStep(IncreaseCash increaseCash) throws InterruptedException {
        Thread.sleep(5000);

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
            incCash(saveProfiles.getUsersEmail(), increaseCash);
        }
    }
}
