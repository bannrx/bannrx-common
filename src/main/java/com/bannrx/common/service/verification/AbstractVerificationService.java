package com.bannrx.common.service.verification;

import com.bannrx.common.dtos.verification.VerificationData;
import com.bannrx.common.dtos.verification.VerificationDto;
import com.bannrx.common.enums.VerificationProcess;
import com.bannrx.common.mappers.VerificationMapper;
import com.bannrx.common.repository.VerificationAuditRepository;
import com.bannrx.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rklab.utility.annotations.Loggable;
import rklab.utility.expectations.InvalidInputException;
import rklab.utility.utilities.ValidationUtils;

/**
 * The abstract class Verification service.
 */
@Service
@Loggable
public abstract class AbstractVerificationService {

    @Autowired protected ValidationUtils validationUtils;
    @Autowired protected UserService userService;
    @Autowired protected VerificationAuditRepository verificationAuditRepository;

    public abstract VerificationDto process(VerificationDto verificationDto) throws InvalidInputException;

    public abstract VerificationProcess getVerificationProcess();

    protected <O extends VerificationData> O castVerificationData(VerificationData verificationData, Class<O> oClass)
            throws InvalidInputException {
        if (oClass.isInstance(verificationData)) {
            return oClass.cast(verificationData);
        } else {
            throw new InvalidInputException(String.format("Invalid type: Expected %s but got %s.",oClass.getSimpleName()
                    , verificationData.getClass().getSimpleName()));
        }
    }

    public void validate(VerificationDto verificationDto) throws InvalidInputException {
        validationUtils.validate(verificationDto);
    }

    protected String getVerifiedBy() throws InvalidInputException {
        return userService.fetchLoggedInUser().getEmail();
    }

    /**
     * Post process.
     *
     * @param verificationDto the verification dto
     * @throws InvalidInputException the invalid input exception
     */
    protected VerificationDto postProcess(VerificationDto verificationDto) throws InvalidInputException {
        var entity = VerificationMapper.INSTANCE.toEntity(
                verificationDto,
                getVerifiedBy()
        );
        var audit = verificationAuditRepository.save(entity);
        verificationDto.setId(audit.getId());
        return verificationDto;
    }


}
