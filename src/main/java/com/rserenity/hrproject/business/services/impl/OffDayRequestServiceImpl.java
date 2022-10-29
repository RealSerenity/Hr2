package com.rserenity.hrproject.business.services.impl;

import com.rserenity.hrproject.Exception.ResourceNotFoundException;
import com.rserenity.hrproject.business.dto.OffDayRequestDto;
import com.rserenity.hrproject.business.dto.UserDto;
import com.rserenity.hrproject.business.services.OffDayRequestServices;
import com.rserenity.hrproject.business.services.OffDayServices;
import com.rserenity.hrproject.business.services.UserServices;
import com.rserenity.hrproject.data.entity.OffDayRequestEntity;
import com.rserenity.hrproject.data.entity.UserEntity;
import com.rserenity.hrproject.data.repository.OffDayRequestRepository;
import com.rserenity.hrproject.security.auth.RequestStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class OffDayRequestServiceImpl implements OffDayRequestServices {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    OffDayRequestRepository offDayRequestRepository;

    @Autowired
    UserServices userServices;

    @Autowired
    OffDayServices offDayServices;


    @Override
    public List<OffDayRequestDto> getRequests() {
        List<OffDayRequestDto> requests = new ArrayList<>();
        Iterable<OffDayRequestEntity> entityList= offDayRequestRepository.findAll();
        for(OffDayRequestEntity entity : entityList){
            OffDayRequestDto offDayDto = entityToDto(entity);
            requests.add(offDayDto);
        }
        return requests;
    }

    @Override
    public List<OffDayRequestDto> getWaitingRequests() {
        List<OffDayRequestDto> requests = new ArrayList<>();
        Iterable<OffDayRequestEntity> entityList= offDayRequestRepository.findAll();
        for(OffDayRequestEntity entity : entityList){
            OffDayRequestDto offDayDto = entityToDto(entity);
            if(offDayDto.getStatus() == RequestStatus.Waiting)
            {
                requests.add(offDayDto);
            }
        }
        return requests;
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> deleteRequest(Long id) throws Throwable {
        OffDayRequestEntity entity = dtoToEntity(getRequestById(id).getBody());
        offDayRequestRepository.delete(entity);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
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
    public ResponseEntity<OffDayRequestDto> getRequestById(Long requestId) {
        OffDayRequestEntity entity = offDayRequestRepository.findById(requestId).orElseThrow(
                ()-> new ResourceNotFoundException("Request not exist by given id " + requestId));
        OffDayRequestDto dto = entityToDto(entity);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<OffDayRequestDto> approveRequest(Long requestId) throws Throwable {
        OffDayRequestDto dto = getRequestById(requestId).getBody();
        if(dto == null){
            System.out.println("Null requestId !!!");
            return null;
        }
        dto.setStatus(RequestStatus.Approved);
        OffDayRequestEntity entity = dtoToEntity(dto);

        // checking user then creating off day according to the user
        if(dto.getUserId() != null & entity.getUser().getId() != null){
            offDayServices.createOffDay(dto.getUserId(), dto.getDate());
            // success message
        }else{
            System.out.println();
            // fail message
        }
        offDayRequestRepository.save(entity);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<OffDayRequestDto> denyRequest(Long requestId) throws Throwable {
        OffDayRequestDto dto = getRequestById(requestId).getBody();
        if(dto == null){
            System.out.println("Null requestId !!!");
            return null;
        }
        dto.setStatus(RequestStatus.Denied);
        OffDayRequestEntity entity = dtoToEntity(dto);
        offDayRequestRepository.save(entity);
        return ResponseEntity.ok(dto);
    }

//    @Override
//    public List<OffDayRequestDto> getRequestsOfUserId(Long userId) throws Throwable {
//        OffDayRequestEntity[] entities = offDayRequestRepository.getRequestsOfUser(userServices.dtoToEntity(userServices.getUserById(userId).getBody()));
//        return Arrays.stream(entities).map(this::entityToDto).toList();
//    }

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

    @Override
    public List<OffDayRequestDto> findAllofIdOrderByDateDesc(Long id) throws Throwable {
        UserEntity entity = userServices.dtoToEntity(userServices.getUserById(id).getBody());
        List<OffDayRequestEntity> entities = offDayRequestRepository.findOffDayRequestEntitiesByUserOrderByRequestDateDescIdDesc(entity);
        return entities.stream().map(this::entityToDto).toList();
    }
}
