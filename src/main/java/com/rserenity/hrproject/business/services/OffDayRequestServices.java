package com.rserenity.hrproject.business.services;

import com.rserenity.hrproject.business.dto.OffDayDto;
import com.rserenity.hrproject.business.dto.OffDayRequestDto;
import com.rserenity.hrproject.data.entity.OffDayEntity;
import com.rserenity.hrproject.data.entity.OffDayRequestEntity;
import com.rserenity.hrproject.data.entity.UserEntity;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface OffDayRequestServices {

    public List<OffDayRequestDto> getRequests();

    public OffDayRequestDto createRequest(Long userId, LocalDate date) throws Throwable;

    public ResponseEntity<OffDayRequestDto> getRequestById(Long requestId) throws Throwable;

    public ResponseEntity<OffDayRequestDto> approveRequest(Long requestId) throws Throwable;

    public ResponseEntity<OffDayRequestDto> denyRequest(Long requestId) throws Throwable;

    public List<OffDayRequestDto> getRequestsOfUserId(Long userId) throws Throwable;

    public OffDayRequestDto entityToDto(OffDayRequestEntity offDayRequest);

    public OffDayRequestEntity dtoToEntity(OffDayRequestDto offDayRequest) throws Throwable;
}
