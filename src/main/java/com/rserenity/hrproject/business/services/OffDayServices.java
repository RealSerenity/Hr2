package com.rserenity.hrproject.business.services;

import com.rserenity.hrproject.business.dto.OffDayDto;
import com.rserenity.hrproject.business.dto.OffDayRequestDto;
import com.rserenity.hrproject.business.dto.UserDto;
import com.rserenity.hrproject.data.entity.OffDayEntity;
import com.rserenity.hrproject.data.entity.UserEntity;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OffDayServices {

    public List<OffDayDto> getOffDays();

    public ResponseEntity<Map<String, Boolean>> deleteOffDay(Long id) throws Throwable;

    public OffDayDto createOffDay(Long userId, LocalDate date) throws Throwable;

    public ResponseEntity<OffDayRequestDto> getRequestById(Long requestId) throws Throwable;

    public List<OffDayDto> getOffDaysByUser(UserDto user) throws Throwable;

    public ResponseEntity<OffDayDto> getOffDayById(Long id) throws Throwable;

    public OffDayDto entityToDto(OffDayEntity offDayEntity);
    public OffDayEntity dtoToEntity(OffDayDto offDayDto) throws Throwable;
}
