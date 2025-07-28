package com.bannrx.common.service;

import com.bannrx.common.persistence.entities.UserProfile;
import com.bannrx.common.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;

    public String delete(UserProfile profile) {
        userProfileRepository.delete(profile);
        return "user profile deleted successfully";
    }
}