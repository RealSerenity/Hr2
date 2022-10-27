package com.rserenity.hrproject.business.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;

@Data
@Log4j2
@Builder
@NoArgsConstructor
public class OffDayDto {

    private Long id;
    private LocalDate date;
    private Long userId;

    public OffDayDto(Long id, LocalDate date, Long userId) {
        this.id = id;
        this.date = date;
        this.userId = userId;
    }
}
