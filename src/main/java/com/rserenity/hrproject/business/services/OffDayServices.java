package com.rserenity.hrproject.business.services;

import com.rserenity.hrproject.business.dto.OffDayDto;
import com.rserenity.hrproject.business.dto.UserDto;
import com.rserenity.hrproject.data.entity.OffDayEntity;
import com.rserenity.hrproject.data.entity.UserEntity;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OffDayServices {

    public List<OffDayDto> getOffDays();

    public OffDayDto createOffDay(Long userId) throws Throwable;

    public List<OffDayDto> getOffDaysByUser(UserEntity user) throws Throwable;

    public ResponseEntity<OffDayDto> getOffDayById(Long id) throws Throwable;

    public OffDayDto entityToDto(OffDayEntity offDayEntity);
    public OffDayEntity dtoToEntity(OffDayDto offDayDto) throws Throwable;
}
