package com.rserenity.hrproject.security.Session;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface JSessionServices{

    public void deleteAll();

    public ResponseEntity<Map<String, Boolean>>  deleteSessionById(String session);
    public List<JSessionDto> getAllSessions();

    public JSessionDto createJSession(JSessionDto jSessionDto);

    public ResponseEntity<JSessionDto> getJSessionById(Long id) throws Throwable;

    JSessionEntity findByJSessionId(String sessionId);

    public JSessionDto entityToDto(JSessionEntity jSessionEntity);
    public JSessionEntity dtoToEntity(JSessionDto jSessionDto);


}
