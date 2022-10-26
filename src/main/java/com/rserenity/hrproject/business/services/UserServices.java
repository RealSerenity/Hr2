package com.rserenity.hrproject.business.services;

import com.rserenity.hrproject.business.dto.UserDto;
import com.rserenity.hrproject.data.entity.OffDayEntity;
import com.rserenity.hrproject.data.entity.UserEntity;
import com.rserenity.hrproject.security.auth.ApplicationUserRole;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserServices extends UserDetailsService {

    public List<UserDto> getAllUsers();

    public UserDto createUser(UserDto userDto);

    public ResponseEntity<UserDto> getUserById(Long id) throws Throwable;

    public ResponseEntity<UserDto> addRoleToUser(Long id, String roleName) throws Throwable;

    public ResponseEntity<UserDto> updateUserById(Long id, UserDto customerDto) throws Throwable;

    public ResponseEntity<Map<String,Boolean>> deleteUser(Long id) throws Throwable;


    public UserDto loadUserDtoByUsername(String username) throws UsernameNotFoundException;

    public UserDto entityToDto(UserEntity userEntity);
    public UserEntity dtoToEntity(UserDto customerDto);
}
