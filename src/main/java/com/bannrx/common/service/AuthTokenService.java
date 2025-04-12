package com.bannrx.common.service;

import com.bannrx.common.dtos.SecurityUserDto;
import com.bannrx.common.enums.TokenStatus;
import com.bannrx.common.persistence.entities.AuthToken;
import com.bannrx.common.persistence.entities.User;
import com.bannrx.common.repository.AuthTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import rklab.utility.expectations.InvalidInputException;
import rklab.utility.services.JWTService;
import rklab.utility.services.JwtConfiguration;

import java.util.Optional;

import static com.bannrx.common.enums.TokenStatus.ACTIVE;
import static com.bannrx.common.enums.TokenStatus.EXPIRED;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthTokenService {

    private final UserService userService;
    private final JWTService jwtService;
    private final JwtConfiguration configuration;
    private final AuthTokenRepository authTokenRepository;

    /**
     * Deactivate.
     *
     * @param authToken the auth token
     */
    public void deactivate(AuthToken authToken){
        authToken.setStatus(EXPIRED);
        authTokenRepository.save(authToken);
    }

    /**
     * Fetch token from
     * if does not exist then creates and gives token
     * if exists and active then fetches token
     * if exists and expired refreshes the token and mark it as active and gets the new token
     *
     * @param username the username
     * @return the string
     * @throws InvalidInputException the invalid input exception
     */
    @Transactional(rollbackOn = Exception.class)
    public String fetchToken(final String username) throws InvalidInputException {
        var user = userService.fetchByUsername(username);
        var authTokenMayBe = Optional.ofNullable(user.getAuthToken());
        if (authTokenMayBe.isPresent()){
            return fetchToken(user, authTokenMayBe.get());
        }
        return createToken(user);
    }

    private String createToken(User user){
        var authToken = create(user);
        return authToken.getToken();
    }

    private AuthToken create(User user){
        var token = generateToken(user);
        var retVal = new AuthToken();
        retVal.setStatus(ACTIVE);
        retVal.setToken(token);
        retVal.map(user);
        retVal.setCreatedBy(user.getEmail());
        retVal.setModifiedBy(user.getEmail());
        userService.saveOrUpdate(user);
        return authTokenRepository.save(retVal);
    }

    private String fetchToken(User user, AuthToken authToken){
        var token = authToken.getToken();
        if (!isActiveToken(token)){
            authToken = updateTokenAndSave(user, authToken);
        }
        return authToken.getToken();
    }

    private AuthToken updateTokenAndSave(User user, AuthToken authToken){
        var token = generateToken(user);
        authToken.setToken(token);
        authToken.setStatus(ACTIVE);
        return authTokenRepository.save(authToken);
    }

    private String generateToken(User user) {
        return jwtService.generateToken(configuration, new SecurityUserDto(user));
    }

    private boolean isActiveToken(String token){
        return jwtService.isActive(token, configuration);
    }

}
