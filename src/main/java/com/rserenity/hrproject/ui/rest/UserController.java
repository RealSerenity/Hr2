package com.rserenity.hrproject.ui.rest;

import com.rserenity.hrproject.Exception.ResourceNotFoundException;
import com.rserenity.hrproject.business.dto.UserDto;
import com.rserenity.hrproject.business.services.UserServices;
import com.rserenity.hrproject.data.entity.UserEntity;
import com.rserenity.hrproject.security.auth.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.rserenity.hrproject.security.auth.ApplicationUserRole.EMPLOYEE;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

    @Autowired
    UserServices userServices;

    @GetMapping("/getAll")
    public List<UserDto> getAllUsers() {
;       return userServices.getAllUsers();
    }

    @PostMapping("/createUser")
    public UserDto createUser(@RequestBody UserDto userDto) {
        userServices.createUser(userDto);
        return userDto;
    }

    @PostMapping("/addRole/{id}/{roleName}")
    public ResponseEntity<UserDto> addRoleToUser(@PathVariable Long id,@PathVariable String roleName) throws Throwable {
        return userServices.addRoleToUser(id,roleName);
    }


    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) throws Throwable {
        return userServices.getUserById(id);
    }
}
