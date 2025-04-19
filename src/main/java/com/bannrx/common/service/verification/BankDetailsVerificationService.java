package com.bannrx.common.service.verification;

import com.bannrx.common.dtos.BankDetailsDto;
import com.bannrx.common.dtos.verification.VerificationDto;
import com.bannrx.common.enums.VerificationProcess;
import com.bannrx.common.validationGroups.VerificationValidationGroup;
import org.springframework.stereotype.Service;
import rklab.utility.annotations.Loggable;
import rklab.utility.expectations.InvalidInputException;

import static com.bannrx.common.enums.VerificationProcess.MANUAL;

/**
 * The responsibility of BankDetailsVerificationService MANUAL process is to set the
 * verification audit and who verified. As this is manual process, it is
 *
 */

@Service
@Loggable
public class BankDetailsVerificationService extends AbstractVerificationService{

    @Override
    public VerificationDto process(VerificationDto verificationDto) throws InvalidInputException {
        var bankDetails = castVerificationData(verificationDto.getVerificationData(), BankDetailsDto.class);
        verificationDto.setVerified(bankDetails.getVerified());
        return postProcess(verificationDto);
    }

    public void validate(VerificationDto verificationDto) throws InvalidInputException {
        super.validate(verificationDto);
        var bankDetailsDto = castVerificationData(verificationDto.getVerificationData(), BankDetailsDto.class);
        validationUtils.validate(bankDetailsDto);
        validationUtils.validate(bankDetailsDto, VerificationValidationGroup.class);
    }

    @Override
    public VerificationProcess getVerificationProcess() {
        return MANUAL;
    }

    @Override
    protected String getVerifiedBy() throws InvalidInputException {
        return userService.fetchLoggedInUser().getId();
    }

}
