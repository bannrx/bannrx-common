package com.bannrx.common.service;

import com.bannrx.common.dtos.BankDetailsDto;
import com.bannrx.common.enums.Status;
import com.bannrx.common.persistence.entities.BankDetails;
import com.bannrx.common.persistence.entities.User;
import com.bannrx.common.repository.BankDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rklab.utility.annotations.Loggable;
import rklab.utility.expectations.ServerException;
import rklab.utility.utilities.ObjectMapperUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;




@Service
@Loggable
@RequiredArgsConstructor
public class BankDetailsService {

    private final BankDetailsRepository bankDetailsRepository;

    @Transactional
    public Set<BankDetails> save(List<BankDetailsDto> bankDetailsDtoList, User user) throws ServerException {
        Set<BankDetails> bankDetails = new HashSet<>();
        for(var dto : bankDetailsDtoList){
            var bankDetail = ObjectMapperUtils.map(dto, BankDetails.class);
            bankDetail.setStatus(Status.ACTIVE);
            bankDetail.setUser(user);
            bankDetail = bankDetailsRepository.save(bankDetail);
            bankDetails.add(bankDetail);
        }
        return bankDetails;
    }
}
