package com.bannrx.common.service.verification;

import com.bannrx.common.dtos.verification.VerificationDto;
import com.bannrx.common.service.ApplicationContextProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationServiceProvider {

    private final ApplicationContextProvider applicationContextProvider;

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
            case BANK_DETAILS -> applicationContextProvider.getApplicationContext().getBean(BankDetailsVerificationService.class);

            default -> throw new UnsupportedOperationException("Verification process not implemented for purpose "+purpose.name());

        };
    }

}
