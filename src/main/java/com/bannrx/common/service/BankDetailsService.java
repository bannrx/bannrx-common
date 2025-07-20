package com.bannrx.common.service;

import com.bannrx.common.dtos.BankDetailsDto;
import com.bannrx.common.persistence.entities.BankDetails;
import com.bannrx.common.repository.BankDetailsRepository;
import com.bannrx.common.service.verification.VerificationAuditService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import rklab.utility.annotations.Loggable;
import rklab.utility.expectations.InvalidInputException;
import rklab.utility.expectations.ServerException;
import rklab.utility.utilities.ObjectMapperUtils;
import java.util.HashSet;
import java.util.Set;




@Service
@Loggable
@RequiredArgsConstructor
public class BankDetailsService {

    private final BankDetailsRepository bankDetailsRepository;
    private final VerificationAuditService verificationAuditService;

    public BankDetailsDto update(BankDetailsDto bankDetailsDto) throws InvalidInputException, ServerException {
        var bankDetails = fetchById(bankDetailsDto.getId());
        ObjectMapperUtils.map(bankDetailsDto, bankDetails);
        bankDetailsRepository.save(bankDetails);
        return toDto(bankDetails);
    }

    /**
     * Validate.
     *
     * @param bankDetailsDto the bank details dto
     * @throws InvalidInputException the invalid input exception
     */
    public void validate(BankDetailsDto bankDetailsDto, String loggedInUserId) throws InvalidInputException {
        var bankDetails = fetchById(bankDetailsDto.getId());
        var bankDetailUserId = bankDetails.getUserProfile().getUser().getId();
        if (!StringUtils.equals(bankDetailUserId, loggedInUserId)){
            throw new UnsupportedOperationException("Bank Details are associated to other user.");
        }
        validateAdd(bankDetailsDto);
    }

    public void validateAdd(BankDetailsDto bankDetailsDto) throws InvalidInputException {
        var exists = existsByAccountNoAndIfscCode(bankDetailsDto.getAccountNo(), bankDetailsDto.getIfscCode());
        if (exists){
            throw new InvalidInputException("Bank details already exists.");
        }
        var verificationAudit = verificationAuditService.fetchById(bankDetailsDto.getVerificationProcessId());
        if (!verificationAudit.getVerified()){
            throw new InvalidInputException("Bank detail are not verified.");
        }
    }

    /**
     * To entity set.
     *
     * @param bankDetailsDtoSet the bank details dto set
     * @return the set or null in case input set is null
     * @throws ServerException the server exception
     */
    public Set<BankDetails> toEntitySet(Set<BankDetailsDto> bankDetailsDtoSet) throws ServerException {
        if (CollectionUtils.isNotEmpty(bankDetailsDtoSet)) {
            Set<BankDetails> bankDetailsSet = new HashSet<>();
            for(var dto : bankDetailsDtoSet){
                var entity = ObjectMapperUtils.map(dto, BankDetails.class);
                bankDetailsSet.add(entity);
            }
            return bankDetailsSet;
        }
        return null;
    }

    /**
     * To dto set.
     *
     * @param bankDetailSet the bank detail set
     * @return the set
     * @throws ServerException the server exception
     */
    public Set<BankDetailsDto> toDto(Set<BankDetails> bankDetailSet) throws ServerException {
        if (CollectionUtils.isNotEmpty(bankDetailSet)) {
            Set<BankDetailsDto> bankDetailsDtoSet = new HashSet<>();
            for(var entity : bankDetailSet){
                var dto = toDto(entity);
                bankDetailsDtoSet.add(dto);
            }
            return bankDetailsDtoSet;
        }
        return null;
    }

    private BankDetailsDto toDto(BankDetails entity) throws ServerException {
        return ObjectMapperUtils.map(entity, BankDetailsDto.class);
    }

    /**
     * Below goes all the database interactions
     *
     * @param id the id
     * @return the bank details
     * @throws InvalidInputException the invalid input exception
     */
    public BankDetails fetchById(final String id) throws InvalidInputException {
        return bankDetailsRepository.findById(id)
                .orElseThrow(() -> new InvalidInputException("Bank details not found by "+id));
    }

    /**
     * Exists by account no and ifsc code
     *
     * @param accountNo the account no
     * @param ifscCode  the ifsc code
     * @return true/false
     */
    public Boolean existsByAccountNoAndIfscCode(final String accountNo, final String ifscCode){
        return bankDetailsRepository.existsByAccountNoAndIfscCode(accountNo, ifscCode);
    }

}
