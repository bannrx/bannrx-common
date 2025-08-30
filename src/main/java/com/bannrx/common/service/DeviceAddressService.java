package com.bannrx.common.service;

import com.bannrx.common.dtos.DAM.DAMRegisterDto;
import com.bannrx.common.dtos.DAM.DAMResponseDto;
import com.bannrx.common.dtos.device.DeviceDto;
import com.bannrx.common.enums.DeviceStatus;
import com.bannrx.common.mappers.DAMMapper;
import com.bannrx.common.persistence.entities.DeviceAddressMapper;
import com.bannrx.common.repository.DAMRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rklab.utility.annotations.Loggable;
import rklab.utility.expectations.InvalidInputException;
import rklab.utility.expectations.ServerException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;




@Service
@Loggable
@RequiredArgsConstructor
public class DAMService {

    private final DAMRepository damRepository;
    private final DeviceService deviceService;
    private final AddressService addressService;
    private final UserService userService;


    @Transactional
    public DAMResponseDto register(DAMRegisterDto dto) throws InvalidInputException, ServerException {
        var loggedInUser = userService.fetchLoggedInUser();
        var user = userService.fetchById(loggedInUser.getId());
        var DAM = DAMMapper.INSTANCE.toEntity(dto, user.getUserProfile());
        DAM.setStatus(DeviceStatus.AVAILABLE);
        DAM = damRepository.save(DAM);
        return toDto(DAM);
    }

    private DAMResponseDto toDto(DeviceAddressMapper dam) throws InvalidInputException, ServerException {
        DeviceDto deviceDto = null;
        if(Objects.nonNull(dam.getDeviceId())) {
            var device = deviceService.fetchById(dam.getDeviceId());
            deviceDto = deviceService.toDto(device);
        }
        var address = addressService.fetchById(dam.getAddressId());
        var addressDto = addressService.toDto(address);
        return new DAMResponseDto(dam.getId(), deviceDto, addressDto, dam.getStatus());
    }

    public Set<DAMResponseDto> toDtoSet(Set<DeviceAddressMapper> mappers) {
        if (Objects.isNull(mappers) || mappers.isEmpty()) return null;
        return mappers.stream().map(dam-> {
            try {
                return toDto(dam);
            } catch (InvalidInputException | ServerException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toSet());
    }

    public Boolean existById(String damId){
        return damRepository.existsById(damId);
    }

    public DAMResponseDto update(String damId) throws InvalidInputException, ServerException {
        var dam = fetchById(damId);
        dam.setDeviceId(null);
        damRepository.save(dam);
        return toDto(dam);
    }

    private DeviceAddressMapper fetchById(String damId) throws InvalidInputException {
        return damRepository.findById(damId)
                .orElseThrow(() -> new InvalidInputException("Invalid Dam id "+ damId));
    }

    public Boolean isDeviceNull(String damId) throws InvalidInputException {
        var dam = fetchById(damId);
        return Objects.isNull(dam.getDeviceId());
    }

    @Transactional
    public String delete(String damId) {
        damRepository.deleteById(damId);
        return "Device Address Mapper Deleted Success";
    }
}
