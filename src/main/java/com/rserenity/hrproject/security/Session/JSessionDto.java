package com.rserenity.hrproject.security.Session;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Data
@NoArgsConstructor
@Log4j2
@Builder
public class JSessionDto {
    private Long id;
    private String jSessionId;
    private Long userId;

    public JSessionDto(Long id, String jSessionId, Long userId) {
        this.id = id;
        this.jSessionId = jSessionId;
        this.userId = userId;
    }
}
