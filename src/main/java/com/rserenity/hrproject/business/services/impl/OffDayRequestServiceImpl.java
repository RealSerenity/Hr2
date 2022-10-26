package com.rserenity.hrproject.business.services.impl;

import com.rserenity.hrproject.Exception.ResourceNotFoundException;
import com.rserenity.hrproject.business.dto.OffDayRequestDto;
import com.rserenity.hrproject.business.services.OffDayRequestServices;
import com.rserenity.hrproject.business.services.UserServices;
import com.rserenity.hrproject.data.entity.OffDayRequestEntity;
import com.rserenity.hrproject.data.repository.OffDayRequestRepository;
import com.rserenity.hrproject.security.auth.RequestStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OffDayRequestServiceImpl implements OffDayRequestServices {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    OffDayRequestRepository offDayRequestRepository;

    @Autowired
    UserServices userServices;


    @Override
    public List<OffDayRequestDto> getRequests() {
        List<OffDayRequestDto> requests = new ArrayList<>();
        Iterable<OffDayRequestEntity> entityList= offDayRequestRepository.findAll();
        for(OffDayRequestEntity entity : entityList){
            OffDayRequestDto offDayDto = entityToDto(entity);

            if(offDayDto.getStatus().equals(RequestStatus.Waiting)) requests.add(offDayDto);

        }
        return requests;
    }

    @Override
    public OffDayRequestDto createRequest(Long userId, LocalDate date) throws Throwable {
        OffDayRequestEntity entity = new OffDayRequestEntity();
        entity.setUser(userServices.dtoToEntity(userServices.getUserById(userId).getBody()));
        System.out.println("Date -> " + date);
        entity.setRequestDate(date);
        entity.setStatus(RequestStatus.Waiting);
        offDayRequestRepository.save(entity);
        return entityToDto(entity);
    }

    @Override
    public ResponseEntity<OffDayRequestDto> getRequestById(Long requestId) throws Throwable {
        OffDayRequestEntity entity = offDayRequestRepository.findById(requestId).orElseThrow(
                ()-> new ResourceNotFoundException("Request not exist by given id " + requestId));
        OffDayRequestDto dto = entityToDto(entity);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<OffDayRequestDto> approveRequest(Long requestId) throws Throwable {
        OffDayRequestDto dto = getRequestById(requestId).getBody();
        OffDayRequestEntity entity = dtoToEntity(dto);
        entity.setStatus(RequestStatus.Approved);
        offDayRequestRepository.save(entity);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<OffDayRequestDto> denyRequest(Long requestId) throws Throwable {
        OffDayRequestDto dto = getRequestById(requestId).getBody();
        OffDayRequestEntity entity = dtoToEntity(dto);
        entity.setStatus(RequestStatus.Denied);
        offDayRequestRepository.save(entity);
        return ResponseEntity.ok(dto);
    }

    @Override
    public List<OffDayRequestDto> getRequestsOfUserId(Long userId) throws Throwable {
        return offDayRequestRepository.getRequestsOfUser(userServices.dtoToEntity(userServices.getUserById(userId).getBody()))
                .stream()
                .map(this::entityToDto).toList();
    }

    @Override
    public OffDayRequestDto entityToDto(OffDayRequestEntity offDayRequest) {
        OffDayRequestDto dto = modelMapper.map(offDayRequest, OffDayRequestDto.class);
        dto.setUserId(offDayRequest.getUser().getId());
        return dto;
    }

    @Override
    public OffDayRequestEntity dtoToEntity(OffDayRequestDto offDayRequest) throws Throwable {
        OffDayRequestEntity entity = modelMapper.map(offDayRequest, OffDayRequestEntity.class);
        entity.setUser(userServices.dtoToEntity(userServices.getUserById(offDayRequest.getUserId()).getBody()));
        return entity;
    }
}
