package com.rserenity.hrproject.ui.rest;

import com.rserenity.hrproject.business.dto.UserDto;
import com.rserenity.hrproject.business.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management/api/employees")
@CrossOrigin(origins = "http://localhost:8080")
public class EmployeeManagementController {

    @Autowired
    UserServices userServices;


    @GetMapping("/getAllEmployees")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<UserDto> getAllEmployees(){
        List<UserDto> allUsers =  userServices.getAllUsers();
        return allUsers;
    }

    @DeleteMapping("/deleteEmployee")
    @PreAuthorize("hasAuthority('employee:write')")
    public void deleteEmployee(){
        System.out.println("deleteEmployee");
    }

    @PostMapping("/createEmployee")
    @PreAuthorize("hasAuthority('employee:write')")
    public void createEmployee(){
        System.out.println("createEmployee");
    }

    @PutMapping("/updateEmployee")
    @PreAuthorize("hasAuthority('employee:write')")
    public void updateEmployee(){
        System.out.println("updateEmployee");
    }

}
