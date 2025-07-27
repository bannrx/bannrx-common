package com.bannrx.common.service;

import com.bannrx.common.dtos.device.DeviceDto;
import com.bannrx.common.dtos.device.DimensionDto;
import com.bannrx.common.dtos.responses.PageableResponse;
import com.bannrx.common.enums.Operator;
import com.bannrx.common.enums.Unit;
import com.bannrx.common.mappers.DeviceMapper;
import com.bannrx.common.mappers.DimensionMapper;
import com.bannrx.common.persistence.entities.Device;
import com.bannrx.common.persistence.entities.UserProfile;
import com.bannrx.common.repository.DeviceRepository;
import com.bannrx.common.searchCriteria.DeviceSearchCriteria;
import com.bannrx.common.specifications.DeviceSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rklab.utility.annotations.Loggable;
import rklab.utility.expectations.InvalidInputException;
import rklab.utility.expectations.ServerException;
import rklab.utility.utilities.ObjectMapperUtils;
import rklab.utility.utilities.PageableUtils;
import java.util.Objects;




@Loggable
@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DimensionService dimensionService;
    private final UserService userService;


    public DeviceDto register(DeviceDto request) throws ServerException, InvalidInputException {
        saveDimension(request.getDimension());
        var loggedInUser = userService.fetchLoggedInUser();
        var user = userService.fetchById(loggedInUser.getId());
        var device = toEntity(request);
        device.setUserProfile(user.getUserProfile());
        device = deviceRepository.save(device);
        return toDto(device);
    }

    private void saveDimension(DimensionDto dto) {
        var dimensionSearchCriteria = DimensionMapper.INSTANCE.toSearchCriteria(dto);
        var dimension = dimensionService.search(dimensionSearchCriteria);
        if(dimension.getContent().isEmpty()){
            dimensionService.save(dimensionSearchCriteria);
        }
    }

    private DeviceDto getDeviceDto(Device device){
        try{
            return toDto(device);
        }catch (ServerException e){
            return null;
        }
    }

    public DeviceDto toDto(Device device) throws ServerException {
        return ObjectMapperUtils.map(device, DeviceDto.class);
    }

    public Device toEntity(DeviceDto request) throws ServerException {
        return DeviceMapper.INSTANCE.toEntity(request);
    }

    @Transactional
    public DeviceDto update(DeviceDto request) throws InvalidInputException, ServerException {
        var device = fetchById(request.getId());
        ObjectMapperUtils.map(request, device);
        device = deviceRepository.save(device);
        return toDto(device);
    }

    private Device fetchById(String id) throws InvalidInputException {
        return deviceRepository.findById(id)
                .orElseThrow(()-> new InvalidInputException(
                        String.format("Invalid device Id %s", id))
                );
    }

    public void delete(String deviceId) {
        deviceRepository.deleteById(deviceId);
    }

    public Boolean existById(String deviceId){
        return deviceRepository.existsById(deviceId);
    }

    public PageableResponse<DeviceDto> fetch(DeviceSearchCriteria searchCriteria) {
        setDefaultIfApplicable(searchCriteria);
        var pageable = PageableUtils.createPageable(searchCriteria);
        var devicePage = deviceRepository.findAll(DeviceSpecification.buildSearchCriteria(searchCriteria), pageable);
        var deviceList = devicePage.getContent().stream()
                .map(this::getDeviceDto)
                .filter(Objects::nonNull)
                .toList();
        return new PageableResponse<>(deviceList,searchCriteria);
    }

    private void setDefaultIfApplicable(DeviceSearchCriteria searchCriteria) {
        if(Objects.nonNull(searchCriteria.getLength()) && Objects.isNull(searchCriteria.getLengthOperator())){
            searchCriteria.setLengthOperator(Operator.EQUALTO);
        }

        if(Objects.nonNull(searchCriteria.getBreadth()) && Objects.isNull(searchCriteria.getBreadthOperator())){
            searchCriteria.setBreadthOperator(Operator.EQUALTO);
        }

        if(Objects.nonNull(searchCriteria.getLength()) || Objects.nonNull(searchCriteria.getBreadth()) && Objects.isNull(searchCriteria.getUnit())){
            searchCriteria.setUnit(Unit.CM);
        }
    }
}
