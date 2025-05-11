package com.bannrx.common.service;

import com.bannrx.common.dtos.device.DeviceDto;
import com.bannrx.common.persistence.entities.Device;
import com.bannrx.common.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rklab.utility.annotations.Loggable;
import rklab.utility.expectations.InvalidInputException;
import rklab.utility.expectations.ServerException;
import rklab.utility.utilities.ObjectMapperUtils;
import java.util.ArrayList;
import java.util.List;


@Loggable
@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceDto register(DeviceDto request) throws ServerException {
        var device = toEntity(request);
        device = deviceRepository.save(device);
        return toDto(device);
    }

    public DeviceDto toDto(Device device) throws ServerException {
        return ObjectMapperUtils.map(device, DeviceDto.class);
    }

    public Device toEntity(DeviceDto request) throws ServerException {
        return ObjectMapperUtils.map(request, Device.class);
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

    public List<DeviceDto> fetchAllDevice() throws ServerException {
        List<Device> deviceList = deviceRepository.findAll();
        List<DeviceDto> deviceDtoList = new ArrayList<>(deviceList.size());
        for(var device: deviceList){
            var dto = toDto(device);
            deviceDtoList.add(dto);
        }
       return deviceDtoList;
    }
}
