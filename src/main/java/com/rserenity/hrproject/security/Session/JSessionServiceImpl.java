package com.rserenity.hrproject.security.Session;

import com.rserenity.hrproject.business.services.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JSessionServiceImpl implements JSessionServices{

    @Autowired
    JSessionRepository jSessionRepository;

    @Autowired
    UserServices userServices;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public void deleteAll() {
        jSessionRepository.deleteAll();
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> deleteSessionById(String session) {
        JSessionEntity entity = findByJSessionId(session);
        jSessionRepository.delete(entity);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public List<JSessionDto> getAllSessions() {
        Iterable<JSessionEntity>  entities = jSessionRepository.findAll();
        List<JSessionDto> dtos = new ArrayList<>();
        for(JSessionEntity entity : entities){
            JSessionDto dto = entityToDto(entity);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public JSessionDto createJSession(JSessionDto jSessionDto) {
        JSessionEntity jSessionEntity = new JSessionEntity();
        jSessionEntity.setJSessionId(jSessionDto.getJSessionId());
        jSessionEntity.setUserId(jSessionDto.getUserId());
        jSessionRepository.save(jSessionEntity);
        return jSessionDto;
    }

    @Override
    public ResponseEntity<JSessionDto> getJSessionById(Long id) throws Throwable {
        return null;
    }

    @Override
    public JSessionEntity findByJSessionId(String sessionId) {
        JSessionEntity entity = jSessionRepository.findByJSessionId(sessionId);
        System.out.println(entity);
        return entity;
    }


    @Override
    public JSessionDto entityToDto(JSessionEntity jSessionEntity) {
        return modelMapper.map(jSessionEntity, JSessionDto.class);
    }

    @Override
    public JSessionEntity dtoToEntity(JSessionDto customerDto) {
        return modelMapper.map(customerDto, JSessionEntity.class);
    }
}
