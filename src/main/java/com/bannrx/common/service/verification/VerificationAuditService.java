package com.bannrx.common.service.verification;

import com.bannrx.common.persistence.entities.VerificationAudit;
import com.bannrx.common.repository.VerificationAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rklab.utility.annotations.Loggable;
import rklab.utility.expectations.InvalidInputException;
import rklab.utility.utilities.ValidationUtils;

@Service
@Loggable
public class VerificationAuditService {

    @Autowired private ValidationUtils validationUtils;
    @Autowired private VerificationAuditRepository verificationAuditRepository;


    /**
     * Below goes all the database interactions.
     *
     */

    public VerificationAudit fetchById(final String id) throws InvalidInputException {
        return verificationAuditRepository.findById(id)
                .orElseThrow(() -> new InvalidInputException("No verification audit found with id "+ id));
    }

}
