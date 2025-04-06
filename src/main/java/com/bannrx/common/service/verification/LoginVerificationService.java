package com.bannrx.common.service.verification;

import com.bannrx.common.dtos.verification.PasswordVerificationData;
import com.bannrx.common.dtos.verification.VerificationDto;
import com.bannrx.common.enums.VerificationProcess;
import com.bannrx.common.mappers.VerificationMapper;
import com.bannrx.common.service.UserService;
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
        postProcess(verificationDto);
        return verificationDto;
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
     * As the while login the security context will not have user. So, overriding common implementation
     * to set created by and modified by manually.
     *
     * @param verificationDto the verification dto
     * @throws InvalidInputException in case logged in user parsing issue
     */
    @Override
    protected void postProcess(VerificationDto verificationDto) throws InvalidInputException {
        var entity = VerificationMapper.INSTANCE.toEntity(
                verificationDto,
                getVerifiedBy()
        );
        entity.setCreatedBy(getVerifiedBy());
        entity.setModifiedBy(getVerifiedBy());
        verificationAuditRepository.save(entity);
    }

}
