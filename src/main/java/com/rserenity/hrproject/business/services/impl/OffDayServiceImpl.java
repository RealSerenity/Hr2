package com.rserenity.hrproject.business.services.impl;

import com.rserenity.hrproject.Exception.ResourceNotFoundException;
import com.rserenity.hrproject.business.dto.OffDayDto;
import com.rserenity.hrproject.business.dto.OffDayRequestDto;
import com.rserenity.hrproject.business.dto.UserDto;
import com.rserenity.hrproject.business.services.OffDayServices;
import com.rserenity.hrproject.business.services.UserServices;
import com.rserenity.hrproject.data.entity.OffDayEntity;
import com.rserenity.hrproject.data.entity.UserEntity;
import com.rserenity.hrproject.data.repository.OffDayRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Log4j2
public class OffDayServiceImpl implements OffDayServices {

    @Autowired
    private OffDayRepository offDayRepository;

    @Autowired
    UserServices userServices;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<OffDayDto> getOffDays() {
        List<OffDayDto> offDayDtos = new ArrayList<>();
        Iterable<OffDayEntity> entityList= offDayRepository.findAll();
        for(OffDayEntity entity : entityList){
            OffDayDto offDayDto = entityToDto(entity);
            offDayDtos.add(offDayDto);
        }
        return offDayDtos;
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> deleteOffDay(Long id) throws Throwable {
        OffDayEntity entity = (OffDayEntity) offDayRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Off-day not exist by given id " + id));
        offDayRepository.delete(entity);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @Override
    public OffDayDto createOffDay(Long userId, LocalDate date) throws Throwable {
        OffDayEntity entity = new OffDayEntity();
        entity.setUser(userServices.dtoToEntity(userServices.getUserById(userId).getBody()));
        entity.setDate(date);
        offDayRepository.save(entity);
        log.info("new Off-day created -> " + entity);
        System.out.println("dto -> " + entityToDto(entity));
        return entityToDto(entity);
    }

    @Override
    public ResponseEntity<OffDayRequestDto> getRequestById(Long requestId) throws Throwable {
        return null;
    }

    @Override
    public List<OffDayDto> getOffDaysByUser(UserDto user) throws Throwable {
        UserEntity entity = userServices.dtoToEntity(user);
        OffDayEntity[] entities = offDayRepository.getAllOfUser(entity);
        return Arrays.stream(entities).map(this::entityToDto).toList();
    }

    @Override
    public ResponseEntity<OffDayDto> getOffDayById(Long id) throws Throwable {
        OffDayEntity entity = offDayRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Offday not exist by given id = " + id));
        OffDayDto dto = entityToDto(entity);
        return ResponseEntity.ok(dto);
    }

    @Override
    public OffDayDto entityToDto(OffDayEntity offDayEntity) {
        OffDayDto dto = modelMapper.map(offDayEntity, OffDayDto.class);
        dto.setDate(offDayEntity.getDate());
        dto.setUserId(offDayEntity.getUser().getId());
        return dto;
    }

    @Override
    public OffDayEntity dtoToEntity(OffDayDto offDayDto) throws Throwable {
        OffDayEntity entity = modelMapper.map(offDayDto, OffDayEntity.class);
        entity.setUser(userServices.dtoToEntity(userServices.getUserById(offDayDto.getUserId()).getBody()));
        entity.setDate(offDayDto.getDate());
        return entity;
    }
}
