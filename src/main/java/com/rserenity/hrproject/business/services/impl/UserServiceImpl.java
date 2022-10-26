package com.rserenity.hrproject.business.services.impl;

import com.rserenity.hrproject.Exception.ResourceNotFoundException;
import com.rserenity.hrproject.business.dto.UserDto;
import com.rserenity.hrproject.business.services.UserServices;
import com.rserenity.hrproject.data.entity.UserEntity;
import com.rserenity.hrproject.data.repository.UserRepository;
import com.rserenity.hrproject.security.auth.ApplicationUserRole;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.rserenity.hrproject.security.auth.ApplicationUserRole.EMPLOYEE;

@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public List<UserDto> getAllUsers() {
        Iterable<UserEntity>  entities = userRepository.findAll();
        List<UserDto> dtos = new ArrayList<>();
        for(UserEntity entity : entities){
           UserDto dto = entityToDto(entity);
           dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setEnabled(true);
        userDto.setAccountNonExpired(true);
        userDto.setCredentialsNonExpired(true);
        userDto.setAccountNonLocked(true);
        UserEntity userEntity = dtoToEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setGrantedAuthorities(EMPLOYEE.getGrantedAuthorities());
        userRepository.save(userEntity);
        return userDto;
    }

    @Override
    public ResponseEntity<UserDto> getUserById(Long id) throws Throwable {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Customer not exist by given id " + id));
        UserDto userDto = entityToDto(userEntity);
        return ResponseEntity.ok(userDto);
    }

    @Override
    public ResponseEntity<UserDto> addRoleToUser(Long id, String roleName) throws Throwable {
        UserDto user = getUserById(id).getBody();
        if (user == null)
        {
            return null;
        }
        UserEntity entity = dtoToEntity(user);
        entity.setGrantedAuthorities(ApplicationUserRole.getRoleByName(roleName).getGrantedAuthorities());
        System.out.println(ApplicationUserRole.getRoleByName(roleName).getGrantedAuthorities());
        System.out.println(entity);
        userRepository.save(entity);
        return ResponseEntity.ok(entityToDto(entity));
    }

    @Override
    public ResponseEntity<UserDto> updateUserById(Long id, UserDto customerDto) throws Throwable {
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> deleteUser(Long id) throws Throwable {
        UserEntity userEntity = (UserEntity) userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Employee not exist by given id " + id));
        userRepository.delete(userEntity);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getAllUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst()
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %s not found", username)));
    }

    @Override
    public UserDto loadUserDtoByUsername(String username) throws UsernameNotFoundException {
        return getAllUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst()
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %s not found", username)));
    }


    @Override
    public UserDto entityToDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserEntity dtoToEntity(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
}
