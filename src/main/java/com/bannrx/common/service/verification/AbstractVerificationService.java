package com.bannrx.common.service.verification;

import com.bannrx.common.dtos.verification.VerificationData;
import com.bannrx.common.dtos.verification.VerificationDto;
import com.bannrx.common.enums.VerificationProcess;
import com.bannrx.common.mappers.VerificationMapper;
import com.bannrx.common.repository.VerificationAuditRepository;
import com.bannrx.common.service.ApplicationContextProvider;
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
    @Autowired private ApplicationContextProvider applicationContextProvider;

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

    /**
     * Fetch service provider abstract verification service.
     * Currently, login is implemented and so switch has only one. When others will be defined it will be more
     *
     * @param verificationDto the verification dto
     * @return the abstract verification service
     */
    public AbstractVerificationService fetchServiceProvider(VerificationDto verificationDto){
        var purpose = verificationDto.getPurpose();
        return switch (purpose){

            case LOGIN -> applicationContextProvider.getApplicationContext().getBean(LoginVerificationService.class);

            default -> throw new UnsupportedOperationException("Verification process not implemented for purpose "+purpose.name());

        };
    }

    protected String getVerifiedBy() throws InvalidInputException {
        return userService.getLoggedInUser().getEmail();
    }

    /**
     * Post process.
     *
     * @param verificationDto the verification dto
     * @throws InvalidInputException the invalid input exception
     */
    protected void postProcess(VerificationDto verificationDto) throws InvalidInputException {
        var entity = VerificationMapper.INSTANCE.toEntity(
                verificationDto,
                getVerifiedBy()
        );
        verificationAuditRepository.save(entity);
    }


}
