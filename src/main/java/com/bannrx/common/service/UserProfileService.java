package com.bannrx.common.service;

import com.bannrx.common.persistence.entities.UserProfile;
import com.bannrx.common.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rklab.utility.expectations.InvalidInputException;


@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;

    public String delete(UserProfile profile) {
        userProfileRepository.delete(profile);
        return "user profile deleted successfully";
    }

    /**
     * Fetches the user profile using the userId
     *
     * @param userId user_id
     * @return userProfile
     * @throws InvalidInputException in case userProfile not found
     */
    public UserProfile fetchByUserId(final String userId) throws InvalidInputException {
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new InvalidInputException("User Profile not found with user id "+userId));
    }

}