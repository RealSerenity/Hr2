package com.rserenity.hrproject.business.services;

import com.rserenity.hrproject.business.dto.OffDayRequestDto;
import com.rserenity.hrproject.data.entity.OffDayRequestEntity;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OffDayRequestServices {

    public List<OffDayRequestDto> getRequests();

    public List<OffDayRequestDto> getWaitingRequests();

    public ResponseEntity<Map<String, Boolean>> deleteRequest(Long id) throws Throwable;

    public OffDayRequestDto createRequest(Long userId, LocalDate date) throws Throwable;

    public ResponseEntity<OffDayRequestDto> getRequestById(Long requestId) throws Throwable;

    public ResponseEntity<OffDayRequestDto> approveRequest(Long requestId) throws Throwable;

    public ResponseEntity<OffDayRequestDto> denyRequest(Long requestId) throws Throwable;

//    public List<OffDayRequestDto> getRequestsOfUserId(Long userId) throws Throwable;

    public OffDayRequestDto entityToDto(OffDayRequestEntity offDayRequest);

    public OffDayRequestEntity dtoToEntity(OffDayRequestDto offDayRequest) throws Throwable;

    // custom queries
    List<OffDayRequestDto> findAllofIdOrderByDateDesc(Long id) throws Throwable;
}
