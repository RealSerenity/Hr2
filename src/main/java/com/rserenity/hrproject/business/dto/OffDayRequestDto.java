package com.rserenity.hrproject.business.dto;

import com.rserenity.hrproject.security.auth.RequestStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Log4j2
@Builder
@NoArgsConstructor
public class OffDayRequestDto {

    Long id;
    Long userId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date;
    RequestStatus status;

    public OffDayRequestDto(Long userId, LocalDate date) {
        this.userId = userId;
        this.date = date;
        this.status = RequestStatus.Waiting;
    }

    public OffDayRequestDto( Long id, Long userId, LocalDate date, RequestStatus status) {
        this.id=id;
        this.userId = userId;
        this.date = date;
        this.status = status;
    }
}
