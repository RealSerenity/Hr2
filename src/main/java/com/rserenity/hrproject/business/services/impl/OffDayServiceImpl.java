package com.rserenity.hrproject.business.services.impl;

import com.rserenity.hrproject.Exception.ResourceNotFoundException;
import com.rserenity.hrproject.business.dto.OffDayDto;
import com.rserenity.hrproject.business.dto.UserDto;
import com.rserenity.hrproject.business.services.OffDayServices;
import com.rserenity.hrproject.business.services.UserServices;
import com.rserenity.hrproject.data.entity.OffDayEntity;
import com.rserenity.hrproject.data.entity.UserEntity;
import com.rserenity.hrproject.data.repository.OffDayRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
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
    public OffDayDto createOffDay(Long userId) throws Throwable {
        OffDayEntity entity = new OffDayEntity();
        entity.setUser(userServices.dtoToEntity(userServices.getUserById(userId).getBody()));
        entity.setDay(LocalDate.now());
        offDayRepository.save(entity);
        return entityToDto(entity);
    }

    @Override
    public List<OffDayDto> getOffDaysByUser(UserEntity user) throws Throwable {
        Set set = offDayRepository.findAllByUser(user);
        List<OffDayDto> arrayList = new ArrayList<>();
        arrayList =  set.stream().toList();
//        while (resultSet.next()) {
//            System.out.println("result set -> " + resultSet.getLong(0));
//            arrayList.add(getOffDayById(resultSet.getLong(0)).getBody());
//        }
        return arrayList;
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
        dto.setUserId(offDayEntity.getUser().getId());
        return dto;
    }

    @Override
    public OffDayEntity dtoToEntity(OffDayDto offDayDto) throws Throwable {
        OffDayEntity entity = modelMapper.map(offDayDto, OffDayEntity.class);
        entity.setUser(userServices.dtoToEntity(userServices.getUserById(offDayDto.getUserId()).getBody()));
        return entity;
    }
}
