package com.bannrx.common.service;

import com.bannrx.common.dtos.campaign.CampaignDto;
import com.bannrx.common.enums.Phase;
import com.bannrx.common.persistence.entities.Campaign;
import com.bannrx.common.repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rklab.utility.expectations.InvalidInputException;
import rklab.utility.expectations.ServerException;
import rklab.utility.utilities.ObjectMapperUtils;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class CampaignService {
    private final CampaignRepository campaignRepository;


    public CampaignDto register(CampaignDto dto) throws ServerException {
        var campaign = toEntity(dto);
        setDefaultValue(campaign);
        campaign = campaignRepository.save(campaign);
        return toDto(campaign);
    }

    private void setDefaultValue(Campaign campaign) {
        campaign.setPhase(Phase.CREATE);
    }

    private CampaignDto toDto(Campaign campaign) throws ServerException {
        return ObjectMapperUtils.map(campaign, CampaignDto.class);
    }

    private Campaign toEntity(CampaignDto dto) throws ServerException {
        return ObjectMapperUtils.map(dto, Campaign.class);
    }

    public void validateCampaignDates(CampaignDto dto) throws InvalidInputException {
        LocalDate today = LocalDate.now();
        LocalDate startDate = dto.getStartDate();
        if(startDate.isBefore(today)){
            throw new InvalidInputException("Start date cannot be in the past.");
        }
        verifyEndDateAfterStart(dto.getEndDate(), startDate);
    }

    private void verifyEndDateAfterStart(LocalDate endDate, LocalDate startDate) throws InvalidInputException {
        if(!endDate.isAfter(startDate)){
            throw new InvalidInputException("End date must be after start date.");
        }
    }

    public void checkEndDateAfterStart(CampaignDto dto) throws InvalidInputException {
        var campaign = fetchById(dto.getId());
        verifyEndDateAfterStart(dto.getEndDate(), campaign.getStartDate());
    }

    public CampaignDto update(CampaignDto dto) throws InvalidInputException, ServerException {
        var campaign = fetchById(dto.getId());
        ObjectMapperUtils.map(dto,campaign);
        campaign = campaignRepository.save(campaign);
        return toDto(campaign);
    }

    public Campaign fetchById(String id) throws InvalidInputException {
        return campaignRepository.findById(id)
                .orElseThrow(() -> new InvalidInputException(String.format("Campaign is not found with %s", id)));
    }

    public boolean canEndDateExtend(CampaignDto dto) {
        return true;
    }

    public boolean isExistById(String id) {
        return campaignRepository.existsById(id);
    }

    @Transactional
    public String delete(String campaignId) throws InvalidInputException {
        campaignRepository.deleteById(campaignId);
        return String.format("Campaign Deleted Successfully %s", campaignId);
    }
}
