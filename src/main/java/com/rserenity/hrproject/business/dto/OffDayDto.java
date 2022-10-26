package com.rserenity.hrproject.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.util.Date;

@Data
@Log4j2
@Builder
@NoArgsConstructor
public class OffDayDto {

    private Long id;
    private LocalDate day;
    private Long userId;

    public OffDayDto(Long id, LocalDate day, Long userId) {
        this.id = id;
        this.day = day;
        this.userId = userId;
    }
}
