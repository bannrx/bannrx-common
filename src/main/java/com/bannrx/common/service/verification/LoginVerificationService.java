package com.bannrx.common.service.verification;

import com.bannrx.common.dtos.verification.PasswordVerificationData;
import com.bannrx.common.dtos.verification.VerificationDto;
import com.bannrx.common.enums.VerificationProcess;
import com.bannrx.common.mappers.VerificationMapper;
import com.bannrx.common.service.UserService;
import com.bannrx.common.validationGroups.VerificationValidationGroup;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rklab.utility.annotations.Loggable;
import rklab.utility.expectations.InvalidInputException;
import rklab.utility.utilities.ValidationUtils;

import static com.bannrx.common.enums.VerificationProcess.PASSWORD;

@Service
@Loggable
public class LoginVerificationService extends AbstractVerificationService{


    @Override
    public VerificationDto process(VerificationDto verificationDto)
            throws InvalidInputException {
        var passwordData = castVerificationData(verificationDto.getVerificationData(), PasswordVerificationData.class);
        var user = userService.fetchByUsername(passwordData.getUsername());
        verificationDto.setVerified(StringUtils.equals(user.getPassword(), passwordData.getPassword()));
        return postProcess(verificationDto);
    }

    @Override
    public VerificationProcess getVerificationProcess() {
        return PASSWORD;
    }

    @Override
    public void validate(VerificationDto verificationDto) throws InvalidInputException {
        super.validate(verificationDto);
        var passwordData = castVerificationData(verificationDto.getVerificationData(), PasswordVerificationData.class);
        validationUtils.validate(passwordData);
        validationUtils.validate(passwordData, VerificationValidationGroup.class);
        var exists = userService.existsByEmailOrPhoneNo(passwordData.getUsername(), passwordData.getUsername());
        if (!exists){
            throw new InvalidInputException("Invalid Username");
        }
    }

    @Override
    protected String getVerifiedBy(){
        return this.getClass().getSimpleName();
    }

    /**
     * Logins count will be more that can lead to huge database space consumption. And so
     * avoiding database record creation.
     *
     * @param verificationDto the verification dto
     */
    @Override
    protected VerificationDto postProcess(VerificationDto verificationDto) {return  verificationDto;}

}
